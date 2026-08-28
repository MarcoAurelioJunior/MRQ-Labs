# Fluxo de Signin

`POST /auth/signin`

1. **Cliente** envia `{ email, senha }`.
2. **AuthController** monta um token de credenciais (`UsernamePasswordAuthenticationToken`) e chama `AuthService.signin(email, senha)`.
3. **AuthService** delega pro [[AuthenticationManager]]: `authenticationManager.authenticate(token)`.
4. O `AuthenticationManager` orquestra por baixo dos panos:
   - Chama `CustomUserDetailsService.loadUserByUsername(email)` (ver [[UserDetailsService]]) pra buscar o `User` no banco e converter pra `UserDetails`.
   - Usa o [[PasswordEncoder]] pra comparar a senha enviada com o hash salvo.
5. **Resultado**:
   - Senha não confere (ou usuário não existe) → `AuthenticationException` é lançada → `AuthService` captura e retorna `false` (401, no futuro).
   - Senha confere → não lança nada → `AuthService` retorna `true` (200, e futuramente aqui entra a geração do JWT — ver [[Roadmap]]).

## Por que não comparamos senha manualmente
Versão antiga (removida) buscava o usuário e chamava `passwordEncoder.matches(...)` direto no service. Problema: duplicava lógica que o Spring Security já resolve via `AuthenticationManager`, e não escalava bem se no futuro quiséssemos suporte a múltiplos métodos de login. Ver [[Por que AuthService separado do UserService]].

## Ver também
[[Fluxo de Signup]], [[Signup revela email duplicado - trade-off]] (mesmo princípio de não vazar qual foi o erro)
