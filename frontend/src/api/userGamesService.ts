import api from "./axiosConfig";
import type { UserGamesRequest, UserGamesResponse } from "../types/userGames";

// returns all games by userId
export const getUserGamesByUser = async (userId: string): Promise<UserGamesResponse[]> => {
  const response = await api.get(`/api/user/${userId}/games`);
  return response.data;
};

// returns all user's games by gameId
export const getUserGamesById = async (userId: string, gameId: string): Promise<UserGamesResponse> => {
  const response = await api.get(`/api/user/${userId}/games/${gameId}`);
  return response.data;
};

// creates user's game
export const createUserGames = async (userId: string, userGames: UserGamesRequest): Promise<UserGamesResponse> => {
  const response = await api.post(`/api/user/${userId}/games`, userGames);
  return response.data;
};

// updates user's games
export const updateUserGames = async (
  userId: string,
  id: string,
  userGames: UserGamesRequest,
): Promise<UserGamesResponse> => {
  const response = await api.put(`/api/user/${userId}/games/${id}`, userGames);
  return response.data;
};

// delete user's games
export const deleteUserGames = async (userId: string, id: string): Promise<void> => {
  await api.delete(`/api/user/${userId}/games/${id}`);
};
