:- dynamic triagem/3.

triagem :- carrega('triagem.bd'),
    format('~n*** Critérios clínicos ***~n~n'),
    repeat,
    paciente(Dado),
    temperatura(Dado),
    freqBatimento(Dado),
    freqResp(Dado),
    paSis(Dado),
    saO2(Dado),
    dispneia(Dado),
    idade(Dado),
    comorbidades(Dado),
    Resposta = n, 
    !,
    salva(paciente, 'triagem.bd').

carrega(Arquivo) :-
    exists_file(Arquivo),
    consult(Arquivo);
    true.

salva(Paciente, Data) :-
    tell(Data),
    listing(Paciente),
    told.

gets(String) :-
    read_line_to_codes(user_input,Char),
    name(String,Char).

responde(Nome) :-
    condicao(Nome, Condicao),
    !,
    format('Condição do paciente ~w é ~w: ~n',[Nome,Condicao]).

%===Perguntas====%

paciente(Dado) :- 
    format('~nNome do paciente: '),
    gets(Dado).

temperatura(Dado) :- 
    format('~nTemperatura corporal: '),
    gets(Temp),
    asserta(criterio(Dado, temperatura, Temp)).

freqBatimento(Dado) :- 
    format('~nQual a frequência de batimentos do paciente: '),
    gets(FreqBat),
    asserta(criterio(Dado, freqBatimento, FreqBat)).

freqResp(Dado) :- 
    format('~nQual a frequência respiratória do paciente: '),
    gets(Resp),
    asserta(criterio(Dado, freqResp, Resp)).

paSis(Dado) :-
    format('~nQual a pressão sistólica do paciente: '),
    gets(Pressao),
    asserta(criterio(Dado, paSis, Pressao)).

saO2(Dado) :-
    format('~nQual a saturação de oxigênio do paciente: '),
    gets(Saturacao),
    asserta(criterio(Dado, saO2, Saturacao)).

dispneia(Dado) :-
    format('~nO paciente esta em dispneia? '),
    gets(Dispneia),
    asserta(criterio(Dado, dispneia, Dispneia)).

idade(Dado) :-
    format('~nQual idade do paciente? '),
    gets(Idade),
    asserta(criterio(Dado, idade, Idade)).

comorbidades(Dado) :-
    format('~nQuantas comorbidades o paciente tem? '),
    gets(Com),
    asserta(criterio(Dado, dispneia, Com)).

% ====== estado ======%

estado(Paciente, gravissimo) :- 
    criterio(Paciente, freqResp, Indicador), Indicador > 30;
    criterio(Paciente, paSis, Indicador), Indicador < 90; 
    criterio(Paciente, saO2, Indicador), Indicador < 95; 
    criterio(Paciente, dispneia, Indicador), Indicador = "sim". 

estado(Paciente, grave) :- 
    criterio(Paciente, temperatura, Indicador), Indicador > 39;
    criterio(Paciente, paSis, Indicador), Indicador >= 90, Indicador =< 100; 
    criterio(Paciente, idade, Indicador), Indicador > 80;
    criterio(Paciente, comorbidades, Indicador), Indicador > 2.
     

estado(Paciente, moderado) :- 
    criterio(Paciente, temperatura, Indicador), (Indicador < 35; (Indicador >= 37, Indicador =< 39)) ;
    criterio(Paciente, freqBatimento, Indicador), Indicador > 100;
    criterio(Paciente, freqResp, Indicador), Indicador > 19, Indicador < 30;
    criterio(Paciente, idade, Indicador), Indicador > 60, Indicador < 79;
    criterio(Paciente, comorbidades, Indicador), Indicador = 1.

estado(Paciente, leve) :- 
    criterio(Paciente, temperatura, Indicador), Indicador > 35 ,Indicador =< 37;
    criterio(Paciente, freqBatimento, Indicador), Indicador < 100;
    criterio(Paciente, freqResp, Indicador), Indicador < 18;
    criterio(Paciente, paSis, Indicador), Indicador > 100;
    criterio(Paciente, saO2, Indicador), Indicador >= 95;
    criterio(Paciente, dispneia, Indicador), Indicador = "nao";
    criterio(Paciente, idade, Indicador), Indicador < 60;
    criterio(Paciente, comorbidades, Indicador), Indicador = 0.