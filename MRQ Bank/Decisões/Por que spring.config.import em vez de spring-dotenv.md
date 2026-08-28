# Por que spring.config.import em vez de spring-dotenv

## Contexto
Pra não commitar credenciais do banco no código (ver [[JAVA_HOME apontando pro JRE errado]] pro debugging relacionado), a ideia era carregar um arquivo `.env` fora do controle do git usando a lib `me.paulschwarz:spring-dotenv`.

## O problema
Mesmo com o `.env` no lugar certo e o `cwd` correto, a aplicação sempre falhava com `Cannot load driver class: ${DRIVER_DB}` — o placeholder nunca era resolvido. Ver [[spring-dotenv incompatível com Boot 4]] pro processo de diagnóstico completo.

## Causa raiz
`spring-dotenv` é de 2023, feito pra Spring Boot 3.x. O projeto usa **Spring Boot 4.1.0** (lançamento recente). O mecanismo de registro da lib (`SpringApplicationRunListener` via `spring.factories`) aparentemente não funciona mais como esperado nessa versão.

## Decisão
Trocar por um recurso **nativo** do Spring Boot: `spring.config.import`, que importa um arquivo de properties externo sem depender de biblioteca nenhuma.

```properties
spring.config.import=optional:file:./secrets.properties
```

`.env` (formato dotenv) virou `secrets.properties` (extensão que o Spring reconhece nativamente).

## Lição
Bibliotecas de terceiros pouco mantidas são um risco real ao usar versões muito novas de um framework. Quando algo "deveria funcionar" e não funciona, vale suspeitar da lib antes de assumir erro de configuração — e preferir mecanismos nativos quando existem, por serem mais estáveis entre versões.

## Ver também
[[spring-dotenv incompatível com Boot 4]]
