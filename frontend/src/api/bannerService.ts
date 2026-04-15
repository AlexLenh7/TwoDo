import api from "./axiosConfig";
import type { BannerRequest, BannerResponse } from "../types/banner";

// returns all banners
export const getAllBanners = async (): Promise<BannerResponse[]> => {
  const response = await api.get("/api/banners");
  return response.data;
};

// returns single banner by id
export const getBannerById = async (id: string): Promise<BannerResponse> => {
  const response = await api.get(`/api/banners/${id}`);
  return response.data;
};

// returns all active banners
export const getActiveBanners = async (): Promise<BannerResponse[]> => {
  const response = await api.get("/api/banners/active");
  return response.data;
};

// returns all banners for game
export const getBannersByGame = async (gameId: string): Promise<BannerResponse[]> => {
  const response = await api.get(`/api/banners/game/${gameId}`);
  return response.data;
};

// create banner
export const createBanner = async (banner: BannerRequest): Promise<BannerResponse> => {
  const response = await api.post("/api/banners", banner);
  return response.data;
};

// updates banner
export const updateBanner = async (id: string, banner: BannerRequest): Promise<BannerResponse> => {
  const response = await api.put(`/api/banners/${id}`, banner);
  return response.data;
};

// deletes a banner
export const deleteBanner = async (id: string): Promise<void> => {
  await api.delete(`api/banners/${id}`);
};
