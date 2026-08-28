# UserDetailsService

Interface **do Spring Security** (`org.springframework.security.core.userdetails.UserDetailsService`) com um único método:

```java
UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
```

## Papel no fluxo
É a "ponte" entre o mundo do banco de dados e o mundo do Spring Security. O [[AuthenticationManager]] chama esse método durante o login pra descobrir quem é o usuário.

**OBS: A ideia seria que o User não se comunica com o Spring Secutiry, sendo um objeto separado e que não consegue passar informação de login e password. Desta forma com o UserDetailsService essa ponte é possível.**
## O erro conceitual que cometi no início
Criei minha própria interface `UserDetailsService` customizada, sem perceber que já existia uma do Spring com o mesmo nome. Isso gerou confusão: o `AuthenticationManager` não sabe nada sobre uma interface minha — ele só reconhece a dele. 

**Nesse caso foi somente uma falta de interpretação, achei que era necessário criar a UserDetailsService, no entanto ele já é implementado com o AuthManager, dessa forma apenas chamados essa função no CustomUserDetailsService chamando a função loadUserByUsername** 

## `User` (entidade) ≠ `UserDetails`
- `User` (minha classe, `mrqlab.bank.user.User`) = "o que existe no banco" (linha da tabela).
- `UserDetails` (do Spring) = "o que o Spring Security entende como identidade pra login" (username, senha, authorities).

A implementação (`CustomUserDetailsService`) busca um `User` no `UserRepository` e **converte** pra `UserDetails` usando o builder `org.springframework.security.core.userdetails.User.withUsername(...).password(...).authorities(...).build()`.

As `authorities` vêm do campo `role` da minha entidade (`user.getRole()`) — não fixei um valor hardcoded, assim um usuário `ADMIN` já é reconhecido automaticamente.

## Ver também
[[Fluxo de Signin]], [[AuthenticationManager]], [[PasswordEncoder]]
