# DTO (Data Transfer Object)

Objeto simples usado só pra transportar dados entre camadas (ex: request HTTP → controller), **separado** da entidade JPA.

## Por que não usar a entidade direto no `@RequestBody`

Hoje `AuthController.signup` recebe `@RequestBody User user` — o `User` é a entidade JPA, mapeada 1:1 com a tabela do banco, incluindo o campo `role`.

Isso é uma vulnerabilidade conhecida: **mass assignment**. Um client mal-intencionado pode mandar `{"role": "ADMIN", ...}` no corpo do signup e se auto-promover a admin, porque o Spring vincula qualquer campo do JSON que exista na classe.

## Solução (planejada, ver [[Roadmap]])
Criar DTOs específicos:
- `SignupRequest` (name, email, password — sem role)
- `SigninRequest` (email, password)
- `UserResponse` (id, name, email — **nunca** a senha, nem hasheada)
- `AuthResponse` (mensagem, e futuramente o token JWT)

O controller recebe/devolve DTOs, e o service converte entre DTO ↔ entidade.

## Ver também
[[Fluxo de Signup]], [[Signup revela email duplicado - trade-off]]
