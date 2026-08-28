# MRQ Bank — Mapa do Projeto

Projeto de portfólio: um backend bancário em Spring Boot, construído do zero com o objetivo principal de **aprender arquitetura e Java/Spring na prática** — não só ter algo funcionando.

## Stack
Spring Boot 4.1 (Java 21), Postgres, Flyway, Spring Security + JWT (jjwt).

## Arquitetura
Pacote por domínio (feature), não por camada — ver [[Package by Feature]].

```
mrqlab.bank
├── auth/          → login, signup, JWT
├── user/          → dados do usuário
├── account/       → conta bancária, saldo (ainda não implementado)
├── transaction/   → depósito, saque, transferência (ainda não implementado)
├── common/        → exceção global, config, utilitários
└── security/      → JwtFilter, SecurityConfig
```

## Conceitos estudados
- [[UserDetailsService]]
- [[AuthenticationManager]]
- [[PasswordEncoder]]
- [[DTO]]
- [[Package by Feature]]

## Fluxos implementados
- [[Fluxo de Signup]]
- [[Fluxo de Signin]]

## Decisões de arquitetura
- [[Por que AuthService separado do UserService]]
- [[Por que spring.config.import em vez de spring-dotenv]]
- [[Signup revela email duplicado - trade-off]]

## Erros e aprendizados de debugging
- [[JAVA_HOME apontando pro JRE errado]]
- [[spring-dotenv incompatível com Boot 4]]

## Próximos passos
Ver [[Roadmap]].
