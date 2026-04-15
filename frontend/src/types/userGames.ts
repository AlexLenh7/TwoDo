export interface UserGamesRequest {
  gameId: string;
  currentCurrency: number;
  currentPityCharacter: number;
  currentPityWeapon: number;
}

export interface UserGamesResponse {
  id: string;
  gameId: string;
  userId: string;
  gameName: string;
  currencyName: string;
  currentCurrency: number;
  currentPityCharacter: number;
  currentPityWeapon: number;
}
