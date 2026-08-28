// Tipos globais compartilhados pela aplicação

export interface User {
  id: string;
  nome: string;
  email: string;
  cpf: string;
}

export interface Account {
  id: string;
  agencia: string;
  numero: string;
  saldo: number;
}

export type TransactionType = 'credito' | 'debito';

export interface Transaction {
  id: string;
  descricao: string;
  categoria: string;
  valor: number;
  tipo: TransactionType;
  data: string; // ISO date
}

export type PixKeyType = 'cpf' | 'email' | 'telefone' | 'aleatoria';

export interface Contact {
  id: string;
  nome: string;
  chavePix?: string;
  tipoChave?: PixKeyType;
}

export type CardStatus = 'ativo' | 'bloqueado';
export type CardKind = 'fisico' | 'virtual';

export interface Card {
  id: string;
  apelido: string;
  numeroFinal: string;
  bandeira: string;
  tipo: CardKind;
  status: CardStatus;
  limite: number;
  limiteDisponivel: number;
}

export interface InvestmentProduct {
  id: string;
  nome: string;
  categoria: string;
  rentabilidadeAno: number; // percentual
  liquidez: string;
  risco: 'baixo' | 'medio' | 'alto';
  valorAplicado: number;
}

export interface Notification {
  id: string;
  titulo: string;
  descricao: string;
  data: string;
  lida: boolean;
  tipo: 'transacao' | 'seguranca' | 'promocao';
}

export interface FaqItem {
  id: string;
  pergunta: string;
  resposta: string;
}
