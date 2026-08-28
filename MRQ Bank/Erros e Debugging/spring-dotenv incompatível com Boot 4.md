# spring-dotenv incompatível com Boot 4

## Sintoma
Aplicação falhava sempre com:
```
Cannot load driver class: ${DRIVER_DB}
```
Mesmo com o `.env` no lugar certo, com todos os 4 valores corretos.

## Processo de descarte de hipóteses
1. **Hipótese 1 — cwd errado no VS Code**: ajustei `cwd` no `launch.json` (ver [[JAVA_HOME apontando pro JRE errado]] pro contexto de rodar via terminal também). Testei via terminal, na pasta certa → **mesmo erro**. Hipótese descartada.
2. **Hipótese 2 — a lib `spring-dotenv` em si**: inspecionei o `.jar` da lib (`me.paulschwarz:spring-dotenv:4.0.0`) e vi que ela se registra via `META-INF/spring.factories` como um `SpringApplicationRunListener` — mecanismo antigo, de uma lib publicada em 2023.

## Causa raiz
O projeto usa **Spring Boot 4.1.0**, uma versão bem mais nova do que a lib foi testada. O mecanismo de `SpringApplicationRunListener` provavelmente muda de comportamento/ordem o suficiente pra lib nunca conseguir injetar as variáveis a tempo do `DataSource` ser criado.

## Solução
Abandonar a lib, usar `spring.config.import` nativo do Spring Boot — ver [[Por que spring.config.import em vez de spring-dotenv]] pra decisão completa.

## Lição de processo de debugging
Isolar variáveis uma de cada vez (trocar só o `cwd`, testar; trocar só o mecanismo de terminal, testar) foi o que permitiu descartar a hipótese errada (VS Code) rapidamente e focar na real (lib desatualizada) — em vez de mudar várias coisas ao mesmo tempo e não saber qual resolveu.
