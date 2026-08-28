# MRQ Bank — Esquema de Arquitetura Frontend

> Stack atual do projeto: Vite + React 19 + TypeScript (sem router, sem estilização, sem state manager ainda).
> Este documento define a estrutura completa para transformar o projeto em um frontend de banco digital.

---

## 1. Visão Geral do Produto

Um banco digital cobre três grandes eixos de funcionalidade:

1. **Acesso e segurança** — login, cadastro, autenticação forte, recuperação de conta
2. **Operação financeira** — saldo, extrato, transferências, pagamentos, cartões, investimentos
3. **Relacionamento** — perfil, notificações, suporte, configurações

---

## 2. Estrutura de Pastas Proposta

```
src/
├── app/                        # Bootstrap da aplicação
│   ├── App.tsx
│   ├── routes.tsx              # Definição central de rotas
│   └── providers/              # Providers globais (Auth, Theme, Query, Toast)
│
├── pages/                      # Uma pasta por tela/rota (composição, sem lógica pesada)
│   ├── auth/
│   │   ├── Login/
│   │   ├── Cadastro/
│   │   ├── RecuperarSenha/
│   │   └── VerificacaoDoisFatores/
│   ├── onboarding/
│   │   └── AberturaDeConta/
│   ├── dashboard/
│   │   └── Home/
│   ├── extrato/
│   │   └── Extrato/
│   ├── transferencias/
│   │   ├── Pix/
│   │   ├── TED/
│   │   └── AgendamentoTransferencia/
│   ├── pagamentos/
│   │   ├── PagarBoleto/
│   │   ├── PagarContas/
│   │   └── Recarga/
│   ├── cartoes/
│   │   ├── MeusCartoes/
│   │   ├── FaturaCartao/
│   │   └── AjustesCartao/
│   ├── investimentos/
│   │   ├── VisaoGeralInvestimentos/
│   │   ├── ProdutoInvestimento/
│   │   └── ResgateAplicacao/
│   ├── emprestimos/
│   │   └── SimuladorEmprestimo/
│   ├── perfil/
│   │   ├── DadosPessoais/
│   │   ├── Seguranca/
│   │   └── Preferencias/
│   ├── notificacoes/
│   │   └── CentralNotificacoes/
│   └── suporte/
│       ├── Chat/
│       ├── FAQ/
│       └── AbrirChamado/
│
├── components/                 # Componentes reutilizáveis de UI
│   ├── ui/                     # Design system: Button, Input, Modal, Card, Badge...
│   ├── layout/                 # Header, Sidebar, BottomNav (mobile), Footer
│   ├── charts/                 # Gráficos de gastos, evolução de saldo
│   └── feedback/                # Toasts, Spinners, EmptyState, ErrorState
│
├── features/                   # Lógica de negócio agrupada por domínio (hooks, services, types)
│   ├── auth/
│   ├── account/                # Conta, saldo
│   ├── transactions/           # Extrato, filtros, categorização
│   ├── transfers/               # Pix, TED, agendamentos
│   ├── payments/                # Boletos, contas, recargas
│   ├── cards/                   # Cartões físicos/virtuais, faturas
│   ├── investments/
│   ├── loans/
│   └── notifications/
│
├── shared/
│   ├── api/                    # Cliente HTTP, interceptors, endpoints
│   ├── hooks/                  # useDebounce, useMediaQuery, usePagination...
│   ├── utils/                  # Formatação de moeda, datas, máscaras (CPF, cartão)
│   ├── constants/
│   └── types/                  # Tipos globais (User, Account, Transaction...)
│
├── store/                      # Estado global (ex: Zustand/Redux) — sessão, saldo em cache
│
├── styles/                     # Tokens de design, temas (light/dark), globals
│
└── assets/                     # Ícones, ilustrações, logos
```

**Racional:** `pages` cuida de composição e roteamento; `features` isola regras de negócio e chamadas de API por domínio; `components/ui` forma o design system puro, sem conhecimento de negócio. Isso permite testar e reaproveitar cada camada isoladamente.

---

## 3. Mapa de Rotas

| Rota | Tela | Autenticação |
|---|---|---|
| `/login` | Login | Pública |
| `/cadastro` | Cadastro / abertura de conta | Pública |
| `/recuperar-senha` | Recuperação de senha | Pública |
| `/verificacao-2fa` | Autenticação em duas etapas | Semi-pública (fluxo de login) |
| `/` | Dashboard (saldo, resumo, atalhos) | Privada |
| `/extrato` | Extrato detalhado com filtros | Privada |
| `/transferir/pix` | Transferência via Pix | Privada |
| `/transferir/ted` | Transferência TED/DOC | Privada |
| `/transferir/agendadas` | Transferências agendadas/recorrentes | Privada |
| `/pagar/boleto` | Pagamento de boleto (leitura/scan) | Privada |
| `/pagar/contas` | Pagamento de contas de consumo | Privada |
| `/cartoes` | Lista de cartões (físico/virtual) | Privada |
| `/cartoes/:id/fatura` | Fatura detalhada, parcelamento | Privada |
| `/cartoes/:id/ajustes` | Bloqueio, limite, senha do cartão | Privada |
| `/investimentos` | Visão geral da carteira | Privada |
| `/investimentos/:produtoId` | Detalhe e aplicação em produto | Privada |
| `/emprestimos/simulador` | Simulação de crédito | Privada |
| `/perfil` | Dados pessoais | Privada |
| `/perfil/seguranca` | Senha, biometria, dispositivos conectados | Privada |
| `/notificacoes` | Central de notificações | Privada |
| `/suporte` | Chat, FAQ, chamados | Privada |

**Proteção de rotas:** wrapper `<RequireAuth>` redirecionando para `/login`; rotas sensíveis (transferências, alteração de senha, cadastro de dispositivo) exigem reautenticação recente ou 2FA.

---

## 4. Funcionalidades Principais (por módulo)

### 4.1 Autenticação e Segurança
- Login com CPF/e-mail + senha, e opção biometria (Web Authn) em navegadores compatíveis
- Autenticação em duas etapas (SMS, app autenticador ou token)
- Cadastro/onboarding com validação de documentos (upload de selfie/documento)
- Recuperação de senha com verificação de identidade
- Gestão de dispositivos confiáveis e sessões ativas (logout remoto)
- Timeout de sessão por inatividade

### 4.2 Dashboard (Home)
- Saldo em conta com opção de ocultar valores
- Resumo de gastos do mês (gráfico simples)
- Atalhos rápidos: Pix, pagar boleto, extrato, cartões
- Últimas transações (preview, com link para extrato completo)
- Avisos/notificações importantes fixadas no topo

### 4.3 Extrato
- Listagem paginada de transações com filtros (período, tipo, categoria, valor)
- Busca textual
- Exportação (PDF/CSV) — ação, não necessariamente implementada no front puro
- Categorização automática de gastos (alimentação, transporte etc.)
- Detalhe de transação (comprovante)

### 4.4 Transferências
- **Pix:** chave (CPF, e-mail, telefone, aleatória), QR Code, Pix Copia e Cola, favoritos
- **TED/DOC:** dados bancários manuais, agenda de contatos
- Agendamento e recorrência (mensal, semanal)
- Confirmação com senha/biometria antes de concluir
- Comprovante pós-transferência (compartilhável)

### 4.5 Pagamentos
- Leitura de boleto (código de barras) e pagamento
- Pagamento de contas de consumo (água, luz, telefone)
- Recarga de celular
- Histórico de pagamentos agendados/recorrentes

### 4.6 Cartões
- Lista de cartões físicos e virtuais, com toggle de bloqueio instantâneo
- Criação de cartão virtual para compras online
- Fatura atual e faturas anteriores, com parcelamento de fatura
- Ajuste de limite (dentro do aprovado) e senha do cartão
- Notificação de compras em tempo real

### 4.7 Investimentos
- Visão consolidada da carteira (renda fixa, fundos, etc.)
- Detalhe de produto: rentabilidade, liquidez, risco
- Fluxo de aplicação e resgate
- Simulador de rentabilidade

### 4.8 Empréstimos/Crédito
- Simulador de empréstimo pessoal (valor, parcelas, taxa)
- Acompanhamento de contratos ativos

### 4.9 Perfil e Configurações
- Dados pessoais e de contato
- Preferências (tema claro/escuro, idioma, notificações)
- Segurança (troca de senha, biometria, dispositivos)
- Limites de transação personalizáveis

### 4.10 Notificações
- Central com histórico (transações, segurança, promoções)
- Preferências de canal (push, e-mail, SMS)

### 4.11 Suporte
- Chat (pode integrar com IA/atendimento humano)
- FAQ pesquisável
- Abertura e acompanhamento de chamados

---

## 5. Fluxos de Usuário Críticos

**Fluxo de login seguro:**
`Login → validação credenciais → 2FA → sessão criada → Dashboard`

**Fluxo de Pix:**
`Dashboard/Atalho → Escolher chave/QR → Definir valor → Revisão → Autenticação (senha/biometria) → Confirmação → Comprovante`

**Fluxo de bloqueio de cartão (ação crítica, precisa ser rápida):**
`Cartões → Selecionar cartão → Bloquear (1 toque, com confirmação) → Feedback imediato`

**Fluxo de onboarding:**
`Cadastro → Dados pessoais → Documento (upload) → Selfie/liveness → Análise → Conta criada → Primeiro acesso`

---

## 6. Gerenciamento de Estado

| Tipo de estado | Solução recomendada |
|---|---|
| Estado de servidor (saldo, extrato, cartões) | React Query / TanStack Query — cache, refetch, invalidação |
| Estado de sessão/autenticação | Context API ou Zustand (store leve) |
| Estado de formulários | React Hook Form + Zod (validação) |
| Estado de UI local (modais, tabs) | useState/useReducer local |

Justificativa: dados financeiros mudam com frequência e exigem revalidação — React Query resolve cache, loading e erro de forma consistente sem reinventar lógica.

---

## 7. Design System e Estilização

- Definir tokens de design (`styles/tokens`): cores, tipografia, espaçamento, raio de borda
- Suporte a tema claro/escuro desde o início (bancos digitais costumam oferecer isso)
- Componentes base acessíveis: `Button`, `Input` (com máscara), `Select`, `Modal`, `Card`, `Badge`, `Tabs`, `Toast`
- Ícones: usar sprite SVG (já existe `public/icons.svg` no projeto) ou biblioteca (lucide-react)
- Recomenda-se **CSS Modules** ou **Tailwind CSS** para escalar estilos sem colisão — decisão pendente conforme preferência do time

---

## 8. Camada de API

```
shared/api/
├── httpClient.ts        # instância axios/fetch com interceptors
├── endpoints.ts          # constantes de endpoints
└── errorHandler.ts       # tratamento padronizado de erros (401 → logout, etc.)
```

- Interceptor de request: injeta token de autenticação
- Interceptor de response: trata expiração de sessão (401) redirecionando para login
- Tipagem forte de request/response por domínio (`features/*/types.ts`)

---

## 9. Segurança no Frontend (pontos de atenção)

- Nunca persistir tokens sensíveis em `localStorage` sem criptografia — preferir cookies `httpOnly` quando possível
- Mascarar dados sensíveis por padrão (saldo, número de cartão) com opção de revelar
- Reautenticação obrigatória antes de ações críticas (transferência, alteração de limite, troca de senha)
- Rate limiting visual (bloqueio temporário de tentativas) no login
- Sanitização de inputs e validação client-side + server-side

---

## 10. Acessibilidade e Responsividade

- Layout mobile-first (a maioria dos usuários de banco digital acessa via celular)
- Navegação inferior (bottom nav) em mobile, sidebar em desktop
- Contraste adequado (WCAG AA), foco visível, labels em todos os inputs
- Suporte a leitores de tela nas ações financeiras críticas (confirmações de valor)

---

## 11. Próximos Passos Sugeridos

1. Definir stack de roteamento (`react-router-dom`) e state (`zustand` + `@tanstack/react-query`)
2. Criar o design system básico (`components/ui`) antes das telas de negócio
3. Implementar fluxo de autenticação completo (é a base de tudo)
4. Implementar Dashboard + Extrato (tela mais usada)
5. Implementar Transferências (Pix) — funcionalidade central de bancos digitais
6. Expandir para Cartões, Pagamentos, Investimentos

---

*Documento gerado como esquema de arquitetura — pronto para servir de base ao scaffold de código quando desejado.*
