import api from "./axiosConfig";
import type { TrackingRequest, TrackingResponse } from "../types/tracking";

// returns tracked banners by id
export const getAllTracking = async (userId: string): Promise<TrackingResponse[]> => {
  const response = await api.get(`/api/user/${userId}/tracking`);
  return response.data;
};

// creates tracker for banner
export const createTracking = async (userId: string, tracking: TrackingRequest): Promise<TrackingResponse> => {
  const response = await api.post(`/api/user/${userId}/tracking`, tracking);
  return response.data;
};

// delete tracking banner
export const deleteTracking = async (userId: string, id: string): Promise<void> => {
  await api.delete(`/api/user/${userId}/tracking/${id}`);
};
