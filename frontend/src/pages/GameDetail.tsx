import { useParams, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext.tsx";
import { useGame, useBannersByGame, useEventsByGame, useTrackedBanners } from "../utils/Usedata.ts";
import BannerCard from "../components/BannerCard";
import EventCard from "../components/EventCard";
import Navbar from "../components/Navbar.tsx";
import { createTracking, deleteTracking } from "../api/trackingService";
import { useState } from "react";

export default function GameDetail() {
  const { gameId } = useParams<{ gameId: string }>();
  const { user, isAuthenticated } = useAuth();
  const [pinLoading, setPinLoading] = useState<string | null>(null);

  const { data: game, loading: gameLoading, error: gameError } = useGame(gameId!);
  const { data: banners, loading: bannersLoading, error: bannersError } = useBannersByGame(gameId!);
  const { data: events, loading: eventsLoading } = useEventsByGame(gameId!);
  const { data: tracked, loading: trackedLoading } = useTrackedBanners(user?.id ?? "");

  const pinnedBannerIds = new Set(tracked?.map((t) => t.bannerId) ?? []);

  const handlePin = async (bannerId: string) => {
    if (!user) return;
    setPinLoading(bannerId);
    try {
      if (pinnedBannerIds.has(bannerId)) {
        const trackingEntry = tracked?.find((t) => t.bannerId === bannerId);
        if (trackingEntry) await deleteTracking(user.id, trackingEntry.id);
      } else {
        await createTracking(user.id, { bannerId });
      }
      // Refresh tracked banners by reloading — in a real app you'd invalidate the query
      window.location.reload();
    } catch {
      alert("Failed to update pin");
    } finally {
      setPinLoading(null);
    }
  };

  if (gameLoading) {
    return (
      <div className="min-h-screen flex flex-col">
        <Navbar />
        <div className="flex items-center justify-center flex-1">
          <p className="text-gray-400">Loading...</p>
        </div>
      </div>
    );
  }

  if (gameError || !game) {
    return (
      <div className="min-h-screen flex flex-col">
        <Navbar />
        <div className="flex flex-col items-center justify-center flex-1 gap-4">
          <p className="text-gray-500">Game not found.</p>
          <Link to="/" className="text-sm text-blue-600 hover:underline">
            ← Back to home
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />

      <main className="flex flex-col gap-12 px-6 py-10 max-w-6xl mx-auto w-full">
        {/* Breadcrumb */}
        <Link to="/" className="text-sm text-gray-400 hover:text-black w-fit">
          ← All games
        </Link>

        {/* Game header */}
        <section className="flex flex-col gap-4">
          <h1 className="text-3xl font-bold text-gray-900">{game.name}</h1>
          <div className="flex flex-wrap gap-4 text-sm text-gray-500">
            <span>
              Currency: <span className="text-gray-800 font-medium">{game.currencyName}</span>
            </span>
            <span>
              Pity limit: <span className="text-gray-800 font-medium">{game.pityLimit} pulls</span>
            </span>
            <span>
              Cost per pull:{" "}
              <span className="text-gray-800 font-medium">
                {game.currencyPerPull} {game.currencyName}
              </span>
            </span>
            <span>
              Daily income:{" "}
              <span className="text-gray-800 font-medium">
                ~{game.dailyIncome} {game.currencyName}
              </span>
            </span>
          </div>

          {!isAuthenticated && (
            <div className="flex items-center gap-3 p-4 bg-gray-50 border border-gray-200 rounded-xl text-sm">
              <span className="text-gray-600">Sign in to track your pity and currency for {game.name}</span>
              <button
                onClick={() => {
                  window.location.href = "http://localhost:8080/oauth2/authorization/google";
                }}
                className="px-4 py-2 bg-black text-white rounded-lg hover:bg-gray-800 shrink-0"
              >
                Sign in
              </button>
            </div>
          )}
        </section>

        {/* Banners */}
        <section className="flex flex-col gap-6">
          <h2 className="text-xl font-semibold text-gray-900">Banners</h2>

          {bannersLoading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {[1, 2, 3].map((i) => (
                <div key={i} className="h-44 bg-gray-100 rounded-xl animate-pulse" />
              ))}
            </div>
          ) : bannersError ? (
            <p className="text-red-500 text-sm">Failed to load banners.</p>
          ) : banners && banners.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {/* Inside the banners.map in GameDetail.tsx */}
              {banners.map((banner) => (
                <BannerCard
                  key={banner.id}
                  banner={banner}
                  onPin={isAuthenticated && !trackedLoading ? handlePin : undefined}
                  isPinned={pinnedBannerIds.has(banner.id)}
                  // This evaluates to true only for the specific banner being processed
                  isLoading={pinLoading === banner.id}
                />
              ))}
            </div>
          ) : (
            <p className="text-gray-400 text-sm">No banners found for this game.</p>
          )}
        </section>

        {/* Events */}
        <section className="flex flex-col gap-6">
          <h2 className="text-xl font-semibold text-gray-900">Events</h2>

          {eventsLoading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {[1, 2].map((i) => (
                <div key={i} className="h-24 bg-gray-100 rounded-xl animate-pulse" />
              ))}
            </div>
          ) : events && events.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {events.map((event) => (
                <EventCard key={event.id} event={event} />
              ))}
            </div>
          ) : (
            <p className="text-gray-400 text-sm">No events found for this game.</p>
          )}
        </section>

        {/* Gacha system guide */}
        <section className="flex flex-col gap-4 p-6 border border-gray-200 rounded-xl">
          <h2 className="text-xl font-semibold text-gray-900">{game.name} gacha system</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm text-gray-600">
            <div className="flex flex-col gap-1">
              <span className="font-medium text-gray-900">Hard pity</span>
              <span>Guaranteed 5★ at {game.pityLimit} pulls</span>
            </div>
            <div className="flex flex-col gap-1">
              <span className="font-medium text-gray-900">Pull cost</span>
              <span>
                {game.currencyPerPull} {game.currencyName} per pull
              </span>
            </div>
            <div className="flex flex-col gap-1">
              <span className="font-medium text-gray-900">Daily income</span>
              <span>
                ~{game.dailyIncome} {game.currencyName} per day
              </span>
            </div>
            <div className="flex flex-col gap-1">
              <span className="font-medium text-gray-900">Pulls per day</span>
              <span>~{(game.dailyIncome / game.currencyPerPull).toFixed(2)} pulls/day</span>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}
