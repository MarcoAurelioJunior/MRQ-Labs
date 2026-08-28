# AuthenticationManager

Orquestrador do login no Spring Security. Recebe credenciais, chama o [[UserDetailsService]] e o [[PasswordEncoder]] por baixo dos panos, e decide se a autenticação é válida.

## Uso básico

```java
authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(email, password)
);
```

- `UsernamePasswordAuthenticationToken` é uma implementação de `Authentication` — quando criada com esse construtor de 2 argumentos, representa uma **tentativa de login ainda não verificada**.
- `authenticate(...)` **não retorna `false`** em caso de erro — ele **lança exceção** (`AuthenticationException` e subtipos, como `BadCredentialsException`). Se o método terminar sem lançar nada, autenticação OK.

```java
try {
    authenticationManager.authenticate(token);
    // autenticado
} catch (AuthenticationException e) {
    // falhou
}
```

## Por que capturar a classe-mãe `AuthenticationException`
O Spring generaliza qualquer falha de login (usuário não existe, senha errada) pra `BadCredentialsException` — de propósito, pra não revelar qual dos dois foi o problema (ver [[Signup revela email duplicado - trade-off]] pra uma discussão parecida sobre vazamento de informação).

## Onde é definido
Como `@Bean` em `SecurityConfig`, usando `AuthenticationConfiguration` — o Spring já sabe montar essa engrenagem sozinho, só precisa expor o bean.

## Ver também
[[Fluxo de Signin]], [[UserDetailsService]], [[Por que AuthService separado do UserService]]
