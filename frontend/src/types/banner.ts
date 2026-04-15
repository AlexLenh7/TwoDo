export interface BannerRequest {
  gameId: string;
  characterName: string;
  bannerType: string;
  startDate: string;
  endDate: string;
}

export interface BannerResponse {
  id: string;
  gameId: string;
  gameName: string;
  characterName: string;
  bannerType: string;
  startDate: string;
  endDate: string;
}
