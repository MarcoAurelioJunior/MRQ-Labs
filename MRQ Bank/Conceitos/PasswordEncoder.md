# PasswordEncoder

Interface do Spring Security com dois métodos principais:
- `encode(String raw)` — transforma senha crua em hash (usa [[BCrypt]] no projeto).
- `matches(String raw, String encoded)` — compara senha crua com o hash salvo.

## Bean definido em `SecurityConfig`

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

## Erro conceitual que cometi
Tentei injetar `SecurityConfig` (a classe de configuração) direto num service, achando que "puxava" o encoder de lá. Errado: você nunca depende da classe de configuração — depende do **tipo do bean que ela produz** (`PasswordEncoder`). O Spring injeta a instância certa em qualquer construtor que peça esse tipo, não importa onde o `@Bean` foi declarado.

## Onde é usado
- `UserService.saveUser`: `passwordEncoder.encode(...)` antes de salvar — nunca salvar senha em texto puro.
- Indiretamente dentro do [[AuthenticationManager]], via [[UserDetailsService]], na hora do login (não chamamos `matches` manualmente mais — isso é o trabalho do `AuthenticationManager`).

## Ver também
[[UserDetailsService]], [[AuthenticationManager]]
