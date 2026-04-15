export interface EventRequest {
  gameId: string;
  name: string;
  totalCurrency: number;
  startDate: string;
  endDate: string;
}

export interface EventResponse {
  id: string;
  gameId: string;
  gameName: string;
  name: string;
  totalCurrency: number;
  startDate: string;
  endDate: string;
}
