import api from "./axiosConfig";
import type { GameRequest, GameResponse } from "../types/game";

// returns all games
export const getAllGames = async (): Promise<GameResponse[]> => {
  const response = await api.get("/api/games");
  return response.data;
};

// returns single game
export const getGame = async (id: string): Promise<GameResponse> => {
  const response = await api.get(`/api/games/${id}`);
  return response.data;
};

// creates game
export const createGame = async (game: GameRequest): Promise<GameResponse> => {
  const response = await api.post("/api/games", game);
  return response.data;
};

// update game
export const updateGame = async (id: string, game: GameRequest): Promise<GameResponse> => {
  const response = await api.put(`/api/games/${id}`, game);
  return response.data;
};

// delete game
export const deleteGame = async (id: string): Promise<void> => {
  await api.delete(`/api/games/${id}`);
};
