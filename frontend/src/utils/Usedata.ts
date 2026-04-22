import { useEffect, useState } from "react";
import { getAllGames, getGame } from "../api/gameService";
import { getBannersByGame, getActiveBanners } from "../api/bannerService";
import { getEventByGame, getActiveEvents } from "../api/eventService";
import { getUserGamesByUser } from "../api/userGamesService";
import { getAllTracking } from "../api/trackingService";
import type { GameResponse } from "../types/game";
import type { BannerResponse } from "../types/banner";
import type { EventResponse } from "../types/event";
import type { UserGamesResponse } from "../types/userGames";
import type { TrackingResponse } from "../types/tracking";

// Generic hook factory to reduce boilerplate
function useFetch<T>(fetcher: () => Promise<T>, deps: unknown[] = []) {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    setLoading(true);
    setError(null);
    fetcher()
      .then(setData)
      .catch(() => setError("Failed to load data"))
      .finally(() => setLoading(false));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps);

  return { data, loading, error };
}

// All supported games
export function useGames() {
  return useFetch<GameResponse[]>(() => getAllGames());
}

// Single game by id
export function useGame(gameId: string) {
  return useFetch<GameResponse>(() => getGame(gameId), [gameId]);
}

// Banners for a specific game
export function useBannersByGame(gameId: string) {
  return useFetch<BannerResponse[]>(() => getBannersByGame(gameId), [gameId]);
}

// Currently active banners across all games
export function useActiveBanners() {
  return useFetch<BannerResponse[]>(() => getActiveBanners());
}

// Events for a specific game
export function useEventsByGame(gameId: string) {
  return useFetch<EventResponse[]>(() => getEventByGame(gameId), [gameId]);
}

// Currently active events across all games
export function useActiveEvents() {
  return useFetch<EventResponse[]>(() => getActiveEvents());
}

// All games a user is tracking with their pity/currency state
export function useUserGames(userId: string) {
  return useFetch<UserGamesResponse[]>(() => getUserGamesByUser(userId), [userId]);
}

// All banners a user has pinned
export function useTrackedBanners(userId: string) {
  return useFetch<TrackingResponse[]>(() => getAllTracking(userId), [userId]);
}
