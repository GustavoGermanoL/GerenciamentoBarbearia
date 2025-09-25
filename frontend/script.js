document.addEventListener('DOMContentLoaded', () => {

    // --- VARIÁVEIS DE ESTADO E CONSTANTES --- //
    const API_BASE_URL = 'http://localhost:8080';
    let state = {
        barbers: [],
        selectedBarber: null,
        selectedService: null,
        selectedDate: null,
        selectedTime: null,
    };

    // --- REFERÊNCIAS DO DOM --- //
    const loader = document.getElementById('loader');
    const appContainer = document.getElementById('app-container');
    const errorMessage = document.getElementById('error-message');

    // Seções (Etapas)
    const stepBarbers = document.getElementById('step-barbers');
    const stepServices = document.getElementById('step-services');
    const stepDateTime = document.getElementById('step-datetime');
    const stepClientInfo = document.getElementById('step-client-info');
    const stepConfirmation = document.getElementById('step-confirmation');
    const allSteps = [stepBarbers, stepServices, stepDateTime, stepClientInfo, stepConfirmation];
    
    // Containers de conteúdo dinâmico
    const barbersList = document.getElementById('barbers-list');
    const servicesList = document.getElementById('services-list');
    const availableTimesContainer = document.getElementById('available-times');

    // Inputs e Formulários
    const datePicker = document.getElementById('date-picker');
    const clientForm = document.getElementById('client-form');
    
    // Botões de navegação
    document.getElementById('back-to-barbers').addEventListener('click', () => showStep('step-barbers'));
    document.getElementById('back-to-services').addEventListener('click', () => showStep('step-services'));
    document.getElementById('back-to-datetime').addEventListener('click', () => showStep('step-datetime'));
    document.getElementById('new-appointment').addEventListener('click', () => {
        resetState();
        loadBarbers();
    });

    // --- FUNÇÕES AUXILIARES --- //

    const showLoader = () => {
        loader.classList.remove('hidden');
        appContainer.classList.add('hidden');
    };

    const hideLoader = () => {
        loader.classList.add('hidden');
        appContainer.classList.remove('hidden');
    };

    const showError = (message) => {
        errorMessage.textContent = message;
        errorMessage.classList.remove('hidden');
    };
    
    const hideError = () => {
        errorMessage.classList.add('hidden');
    };
    
    const showStep = (stepIdToShow) => {
        hideError();
        allSteps.forEach(step => {
            if (step.id === stepIdToShow) {
                step.classList.remove('hidden');
            } else {
                step.classList.add('hidden');
            }
        });
    };
    
    const resetState = () => {
        state = {
            barbers: state.barbers, // Mantém a lista de barbeiros para não recarregar
            selectedBarber: null,
            selectedService: null,
            selectedDate: null,
            selectedTime: null,
        };
        clientForm.reset();
        datePicker.value = '';
    };

    // --- LÓGICA DO FLUXO DE AGENDAMENTO --- //

    /**
     * Etapa 1: Carrega e exibe os barbeiros
     */
    async function loadBarbers() {
        showLoader();
        try {
            const response = await fetch(`${API_BASE_URL}/api/barbeiros`);
            if (!response.ok) {
                throw new Error('Não foi possível carregar os barbeiros.');
            }
            state.barbers = await response.json();
            
            barbersList.innerHTML = ''; // Limpa a lista
            state.barbers.forEach(barber => {
                const barberCard = document.createElement('div');
                barberCard.className = 'card';
                barberCard.innerHTML = `<h3>${barber.nome}</h3>`;
                barberCard.addEventListener('click', () => {
                    state.selectedBarber = barber;
                    displayServices();
                });
                barbersList.appendChild(barberCard);
            });
            showStep('step-barbers');
        } catch (error) {
            showError(error.message || 'Erro ao conectar com o servidor.');
        } finally {
            hideLoader();
        }
    }

    /**
     * Etapa 2: Exibe os serviços do barbeiro selecionado
     */
    function displayServices() {
        servicesList.innerHTML = '';
        state.selectedBarber.servicos.forEach(service => {
            const serviceItem = document.createElement('div');
            serviceItem.className = 'list-item';
            serviceItem.innerHTML = `
                <div class="service-details">
                    <div class="name">${service.nome}</div>
                    <div class="meta">${service.duracaoMinutos} min</div>
                </div>
                <div class="service-price">R$ ${service.preco.toFixed(2)}</div>
            `;
            serviceItem.addEventListener('click', () => {
                state.selectedService = service;
                displayDateTimePicker();
            });
            servicesList.appendChild(serviceItem);
        });
        showStep('step-services');
    }

    /**
     * Etapa 3 (Parte 1): Exibe o seletor de data
     */
    function displayDateTimePicker() {
        datePicker.value = ''; // Limpa a data anterior
        availableTimesContainer.innerHTML = '<p>Selecione uma data para ver os horários.</p>';
        showStep('step-datetime');
    }

    /**
     * Etapa 3 (Parte 2): Carrega horários disponíveis para a data selecionada
     */
    async function loadAvailableTimes() {
        state.selectedDate = datePicker.value;
        if (!state.selectedDate) return;

        availableTimesContainer.innerHTML = '<div class="spinner"></div>'; // Feedback de carregamento local

        try {
            const { id: barbeiroId } = state.selectedBarber;
            const { id: servicoId } = state.selectedService;
            const params = new URLSearchParams({ barbeiroId, servicoId, data: state.selectedDate });

            const response = await fetch(`${API_BASE_URL}/api/disponibilidade?${params}`);
            if (!response.ok) {
                throw new Error('Não foi possível buscar os horários.');
            }
            const times = await response.json();

            availableTimesContainer.innerHTML = '';
            if (times.length === 0) {
                availableTimesContainer.innerHTML = '<p>Nenhum horário disponível para esta data.</p>';
                return;
            }

            times.forEach(time => {
                const timeSlot = document.createElement('div');
                timeSlot.className = 'time-slot';
                timeSlot.textContent = time.substring(0, 5); // Formata para "HH:MM"
                timeSlot.addEventListener('click', () => {
                    state.selectedTime = time;
                    displayClientForm();
                });
                availableTimesContainer.appendChild(timeSlot);
            });
        } catch (error) {
            availableTimesContainer.innerHTML = `<p style="color: var(--error-color);">${error.message}</p>`;
        }
    }
    
    /**
     * Etapa 4: Exibe o formulário de informações do cliente
     */
    function displayClientForm() {
        showStep('step-client-info');
    }

    /**
     * Etapa 5: Envia o agendamento para a API
     */
    async function handleBookingSubmit(event) {
        event.preventDefault();
        showLoader();

        const clientName = document.getElementById('client-name').value;
        const clientPhone = document.getElementById('client-phone').value;

        const requestBody = {
            cliente: {
                nome: clientName,
                telefone: clientPhone
            },
            barbeiroId: state.selectedBarber.id,
            servicoId: state.selectedService.id,
            dataHoraInicio: `${state.selectedDate}T${state.selectedTime}`
        };

        try {
            const response = await fetch(`${API_BASE_URL}/api/agendamentos`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (response.status !== 201) { // 201 Created é o esperado para POST bem-sucedido
                const errorData = await response.json().catch(() => null);
                throw new Error(errorData?.message || 'Falha ao realizar o agendamento.');
            }
            
            showStep('step-confirmation');

        } catch (error) {
            showError(error.message);
            showStep('step-client-info'); // Volta para o formulário em caso de erro
        } finally {
            hideLoader();
        }
    }


    // --- INICIALIZAÇÃO E EVENT LISTENERS --- //
    datePicker.addEventListener('change', loadAvailableTimes);
    clientForm.addEventListener('submit', handleBookingSubmit);
    
    // Define a data mínima do datepicker para hoje
    const today = new Date().toISOString().split('T')[0];
    datePicker.setAttribute('min', today);

    // Inicia a aplicação
    loadBarbers();
});