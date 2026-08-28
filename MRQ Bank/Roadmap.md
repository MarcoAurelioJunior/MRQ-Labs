# Roadmap

Ordem planejada de implementação — cada item é um "capítulo" de aprendizado.

- [x] Base de auth: [[UserDetailsService]], [[PasswordEncoder]], [[AuthenticationManager]]
- [x] Separar signup ([[Fluxo de Signup]]) de signin ([[Fluxo de Signin]]) em serviços diferentes
- [ ] Exception handling global (`@RestControllerAdvice`) — em andamento
- [ ] DTOs de request/resposta (não expor a entidade `User` direto) — ver [[DTO]]
- [ ] Geração de JWT de verdade no signin + validação no `JwtAuthenticationFilter`
- [ ] Domínio `account`: criar conta, consultar saldo
- [ ] Domínio `transaction`: depósito e saque (introduz `@Transactional`)
- [ ] Transferência entre contas (atomicidade — a parte mais delicada)
- [ ] Extrato paginado
- [ ] Papéis/permissões (admin vs cliente)
- [ ] Testes (unitários de service + integração)

## Por que essa ordem
Auth primeiro porque tudo depende dele. Account/Transaction depois, porque são o "coração" do domínio bancário e precisam da base de auth já sólida. Testes por último nesse roadmap não significa "não importa" — significa que faz mais sentido escrever teste depois que a lógica core existe, embora idealmente devessem vir junto de cada feature nova daqui pra frente.
