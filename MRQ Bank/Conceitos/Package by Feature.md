# Package by Feature (vs Package by Layer)

## Antes
```
Controllers/
Services/
Repository/
models/
```
Um pacote por **camada técnica**. Funciona bem pra CRUD simples, mas mistura domínios diferentes (User, Account, Transaction) dentro da mesma pasta conforme o projeto cresce.

## Depois
```
auth/
user/
account/
transaction/
security/
common/
```
Um pacote por **domínio/feature**, cada um com suas próprias camadas dentro. É como sistemas bancários reais costumam se organizar — cada domínio é quase um "mini serviço", o que facilita até uma futura migração pra microsserviços.

## Erro cometido na migração
Ao mover os arquivos, dois pacotes ficaram com nomes inconsistentes (`mrqlab.bank.User` com U maiúsculo em alguns arquivos, `mrqlab.bank.user` minúsculo em outros). Funcionou por acidente no Windows (filesystem case-insensitive), mas quebraria em Linux (case-sensitive) — lição: convenção de nomes de pacote em Java é sempre minúscula, e isso não é só estilo, pode ser bug real dependendo do SO.

## Ver também
[[00 - Mapa do Projeto]], [[Por que AuthService separado do UserService]]
