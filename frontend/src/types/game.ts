export interface GameRequest {
  name: string;
  currencyName: string;
  pityLimit: number;
  currencyPerPull: number;
  dailyIncome: number;
}

export interface GameResponse {
  id: string;
  name: string;
  currencyName: string;
  pityLimit: number;
  currencyPerPull: number;
  dailyIncome: number;
}
