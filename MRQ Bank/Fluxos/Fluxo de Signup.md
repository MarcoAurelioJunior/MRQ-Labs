# Fluxo de Signup

`POST /auth/signup`

1. `AuthController.signup` recebe o `User` do corpo da requisição (⚠️ direto na entidade — ver [[DTO]] pra por que isso deveria mudar).
2. Chama `UserService.saveUser(user)`.
3. `UserService` usa o [[PasswordEncoder]] pra hashear a senha (`passwordEncoder.encode(...)`) **antes** de salvar — nunca senha em texto puro no banco.
4. Salva via `UserRepository.save(user)`.

## Pendências conhecidas
- Não valida se o email já existe antes de salvar (ver [[Signup revela email duplicado - trade-off]] e [[Roadmap]] — exception handling ainda não implementado).
- Retorno hoje é `void` (200 vazio) — planejado trocar por `ResponseEntity<String>` com mensagem, ou DTO completo depois.
- `@RequestBody User` expõe o campo `role` pro client (mass assignment) — corrigir com DTO.

## Ver também
[[Fluxo de Signin]], [[Por que AuthService separado do UserService]]
