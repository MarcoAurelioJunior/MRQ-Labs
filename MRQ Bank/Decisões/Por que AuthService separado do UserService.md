# Por que AuthService separado do UserService

## O problema
`UserService` originalmente tinha dois métodos: `saveUser` (cadastro) e `signinUser` (login, comparando senha manualmente com `passwordEncoder.matches`).

## Por que separar
Cadastro e autenticação são **responsabilidades diferentes**:
- **Cadastro** (`saveUser`) = criar um registro de usuário. Pertence ao domínio `user`.
- **Login** (`signin`) = verificar identidade usando o mecanismo do Spring Security ([[AuthenticationManager]]). É uma preocupação de **autenticação**, não de "dados do usuário" — pertence ao domínio `auth`.

## Resultado
- `UserService` (pacote `user`) ficou só com `saveUser`, injetando `PasswordEncoder` pra hashear a senha antes de salvar.
- `AuthService` (pacote `auth`, novo) ficou com `signin`, injetando `AuthenticationManager` — sem comparar senha manualmente, delega isso pro Spring.

## Efeito colateral bom
Depois dessa separação, o `signinUser` antigo (comparação manual) virou código morto e foi removido — evitando ter duas formas diferentes (e potencialmente divergentes) de fazer a mesma verificação de senha no projeto.

## Ver também
[[Fluxo de Signin]], [[AuthenticationManager]], [[Package by Feature]]
