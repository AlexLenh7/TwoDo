export interface TrackingRequest {
  bannerId: string;
}

export interface TrackingResponse {
  id: string;
  userId: string;
  bannerId: string;
  characterName: string;
  gameName: string;
  bannerType: string;
}
