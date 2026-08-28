# Signup revela email duplicado — trade-off

## A pergunta
Se o `/auth/signup` responde "email já cadastrado" quando o email já existe, isso não é uma brecha de segurança? Um atacante poderia usar isso pra descobrir quais emails são clientes do banco (**user enumeration**).

## Análise
Sim, tecnicamente é uma forma de user enumeration. Mas a severidade não é igual em todo lugar:
- **No signup**: risco considerado baixo pela maioria dos produtos de mercado (Gmail, bancos digitais, etc. revelam isso). A informação vazada é só "esse email já tem conta em algum lugar".
- **No login**: risco mais sério — diferenciar "usuário não existe" de "senha errada" ajuda um atacante a validar listas de credenciais roubadas (credential stuffing). Por isso o [[Fluxo de Signin]] **não** diferencia esses dois casos — ambos retornam a mesma falha genérica.
- **No "esqueci minha senha"** (ainda não implementado): mesmo cuidado do login se aplica.

## Decisão
Manter a mensagem clara "email já cadastrado" no signup (padrão de mercado aceito), e manter a resposta genérica no signin (já implementado assim desde o início, via [[AuthenticationManager]]).

## Por que documentar isso
Pra portfólio, decidir isso **conscientemente** e explicar o porquê vale mais do que aplicar cegamente "a opção mais segura possível" em todo lugar — mostra entendimento de risco, não só medo genérico de vulnerabilidade.

## Ver também
[[DTO]], [[Fluxo de Signin]]
