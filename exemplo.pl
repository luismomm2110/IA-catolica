mamifero(X) :- peludo(X); lacteo(X).
ave(X) :- voa(X), oviparo(X); tem_pena(X).
carnivoro(X) :- dentes(X), olhos_frente(X), garras(X); come_carne(X).
ungulado(X) :- mamifero(X), tem_cascas(X); mamifero(X), rumina(X).
puma(X) :- mamifero(X), manchas_negras(X), carnivoro(X), fulva(X).
tigre(X) :- mamifero(X), manchas_negras(X), carnivoro(X), fulva(X).
girafa(X) :- pescoco_comprido(X), ungulado(X), pernas_compridas(X), manchas_negras(X).
zebra(X) :- listas_preta(X), ungulado(X).
avestruz(X) :- not(voa(X)), preto_branco(X), pescoco_comprido(X).
pinguim(X) :- not(voa(X)), nada(X), preto_branco(X).
albatroz(X) :- ave(X), voa(X).
morcego(X) :- ave(X), mamifero(X).
baleia(X) :- not(peludo(X)), mamifero(X).
