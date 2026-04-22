import { useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext.tsx";
import { useUserGames, useTrackedBanners, useGames, useActiveBanners, useActiveEvents } from "../utils/Usedata.ts";
import PityTracker from "../components/PityTracker.tsx";
import BannerCard from "../components/BannerCard.tsx";
import EventCard from "../components/EventCard.tsx";
import Navbar from "../components/Navbar.tsx";
import { createUserGames, deleteUserGames } from "../api/userGamesService";
import { deleteTracking } from "../api/trackingService";

type DashboardTab = "overview" | "games" | "banners" | "events" | "settings";

export default function Dashboard() {
  const { user } = useAuth();
  const [activeTab, setActiveTab] = useState<DashboardTab>("overview");
  const [refreshKey, setRefreshKey] = useState(0);

  const userId = user?.id ?? "";

  const { data: userGames, loading: userGamesLoading } = useUserGames(userId);
  const { data: tracked, loading: trackedLoading } = useTrackedBanners(userId);
  const { data: allGames } = useGames();
  const { data: activeBanners } = useActiveBanners();
  const { data: activeEvents } = useActiveEvents();

  const refresh = () => setRefreshKey((k) => k + 1);

  // Games the user hasn't added yet
  const trackedGameIds = new Set(userGames?.map((ug) => ug.gameId) ?? []);
  const untrackedGames = allGames?.filter((g) => !trackedGameIds.has(g.id)) ?? [];

  const handleAddGame = async (gameId: string) => {
    try {
      await createUserGames(userId, {
        gameId,
        currentCurrency: 0,
        currentPityCharacter: 0,
        currentPityWeapon: 0,
      });
      refresh();
    } catch {
      alert("Failed to add game");
    }
  };

  const handleRemoveGame = async (userGameId: string) => {
    if (!confirm("Remove this game from your dashboard?")) return;
    try {
      await deleteUserGames(userId, userGameId);
      refresh();
    } catch {
      alert("Failed to remove game");
    }
  };

  const handleUnpin = async (trackingId: string) => {
    try {
      await deleteTracking(userId, trackingId);
      refresh();
    } catch {
      alert("Failed to unpin banner");
    }
  };

  const tabs: { id: DashboardTab; label: string }[] = [
    { id: "overview", label: "Overview" },
    { id: "games", label: "My games" },
    { id: "banners", label: "Pinned banners" },
    { id: "events", label: "Events" },
    { id: "settings", label: "Settings" },
  ];

  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />

      <main className="flex flex-col gap-8 px-6 py-10 max-w-6xl mx-auto w-full">
        {/* Header */}
        <div className="flex items-center justify-between">
          <div className="flex flex-col gap-1">
            <h1 className="text-2xl font-bold text-gray-900">Welcome back, {user?.name?.split(" ")[0]}</h1>
            <p className="text-sm text-gray-500">
              Tracking {userGames?.length ?? 0} game
              {userGames?.length !== 1 ? "s" : ""}
            </p>
          </div>
        </div>

        {/* Tab nav */}
        <div className="flex gap-1 border-b border-gray-200">
          {tabs.map((tab) => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={`px-4 py-2 text-sm font-medium border-b-2 transition-colors ${
                activeTab === tab.id ? "border-black text-black" : "border-transparent text-gray-500 hover:text-black"
              }`}
            >
              {tab.label}
            </button>
          ))}
        </div>

        {/* Overview tab */}
        {activeTab === "overview" && (
          <div className="flex flex-col gap-8">
            {/* Pity trackers */}
            <section className="flex flex-col gap-4">
              <h2 className="text-lg font-semibold text-gray-900">Pity & currency</h2>
              {userGamesLoading ? (
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {[1, 2].map((i) => (
                    <div key={i} className="h-64 bg-gray-100 rounded-xl animate-pulse" />
                  ))}
                </div>
              ) : userGames && userGames.length > 0 ? (
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {userGames.map((ug) => (
                    <PityTracker key={`${ug.id}-${refreshKey}`} userGame={ug} userId={userId} onUpdate={refresh} />
                  ))}
                </div>
              ) : (
                <div className="flex flex-col items-center gap-3 py-10 border border-dashed border-gray-200 rounded-xl">
                  <p className="text-gray-400 text-sm">No games tracked yet</p>
                  <button onClick={() => setActiveTab("games")} className="text-sm text-black underline">
                    Add your first game
                  </button>
                </div>
              )}
            </section>

            {/* Pinned banners preview */}
            {tracked && tracked.length > 0 && (
              <section className="flex flex-col gap-4">
                <div className="flex items-center justify-between">
                  <h2 className="text-lg font-semibold text-gray-900">Pinned banners</h2>
                  <button onClick={() => setActiveTab("banners")} className="text-sm text-gray-400 hover:text-black">
                    View all →
                  </button>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  {tracked.slice(0, 3).map((t) => {
                    const banner = activeBanners?.find((b) => b.id === t.bannerId);
                    if (!banner) return null;
                    return <BannerCard key={t.id} banner={banner} onPin={() => handleUnpin(t.id)} isPinned />;
                  })}
                </div>
              </section>
            )}

            {/* Active events preview */}
            {activeEvents && activeEvents.length > 0 && (
              <section className="flex flex-col gap-4">
                <div className="flex items-center justify-between">
                  <h2 className="text-lg font-semibold text-gray-900">Current events</h2>
                  <button onClick={() => setActiveTab("events")} className="text-sm text-gray-400 hover:text-black">
                    View all →
                  </button>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {activeEvents.slice(0, 4).map((event) => (
                    <EventCard key={event.id} event={event} />
                  ))}
                </div>
              </section>
            )}
          </div>
        )}

        {/* Games tab */}
        {activeTab === "games" && (
          <div className="flex flex-col gap-8">
            <section className="flex flex-col gap-4">
              <h2 className="text-lg font-semibold text-gray-900">Tracked games</h2>
              {userGamesLoading ? (
                <p className="text-gray-400 text-sm">Loading...</p>
              ) : userGames && userGames.length > 0 ? (
                <div className="flex flex-col gap-3">
                  {userGames.map((ug) => (
                    <div
                      key={ug.id}
                      className="flex items-center justify-between p-4 border border-gray-200 rounded-xl"
                    >
                      <div className="flex flex-col gap-1">
                        <span className="font-medium text-gray-900">{ug.gameName}</span>
                        <span className="text-sm text-gray-500">
                          {ug.currentCurrency} {ug.currencyName} · {ug.currentPityCharacter} pity
                        </span>
                      </div>
                      <div className="flex items-center gap-3">
                        <Link to={`/games/${ug.gameId}`} className="text-sm text-gray-400 hover:text-black">
                          View game
                        </Link>
                        <button
                          onClick={() => handleRemoveGame(ug.id)}
                          className="text-sm text-red-500 hover:text-red-700"
                        >
                          Remove
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <p className="text-gray-400 text-sm">No games tracked yet.</p>
              )}
            </section>

            {untrackedGames.length > 0 && (
              <section className="flex flex-col gap-4">
                <h2 className="text-lg font-semibold text-gray-900">Add a game</h2>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                  {untrackedGames.map((game) => (
                    <div key={game.id} className="flex flex-col gap-3 p-4 border border-gray-200 rounded-xl">
                      <span className="font-medium text-gray-900">{game.name}</span>
                      <span className="text-sm text-gray-500">
                        {game.currencyName} · {game.pityLimit} pity
                      </span>
                      <button
                        onClick={() => handleAddGame(game.id)}
                        className="px-4 py-2 text-sm bg-black text-white rounded-lg hover:bg-gray-800"
                      >
                        Add to dashboard
                      </button>
                    </div>
                  ))}
                </div>
              </section>
            )}
          </div>
        )}

        {/* Pinned banners tab */}
        {activeTab === "banners" && (
          <div className="flex flex-col gap-6">
            <h2 className="text-lg font-semibold text-gray-900">Pinned banners</h2>
            {trackedLoading ? (
              <p className="text-gray-400 text-sm">Loading...</p>
            ) : tracked && tracked.length > 0 ? (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {tracked.map((t) => {
                  const banner = activeBanners?.find((b) => b.id === t.bannerId);
                  if (!banner) return null;
                  return <BannerCard key={t.id} banner={banner} onPin={() => handleUnpin(t.id)} isPinned />;
                })}
              </div>
            ) : (
              <div className="flex flex-col items-center gap-3 py-10 border border-dashed border-gray-200 rounded-xl">
                <p className="text-gray-400 text-sm">No pinned banners yet</p>
                <Link to="/" className="text-sm text-black underline">
                  Browse banners on home page
                </Link>
              </div>
            )}
          </div>
        )}

        {/* Events tab */}
        {activeTab === "events" && (
          <div className="flex flex-col gap-6">
            <h2 className="text-lg font-semibold text-gray-900">Current events</h2>
            {activeEvents && activeEvents.length > 0 ? (
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {activeEvents.map((event) => (
                  <EventCard key={event.id} event={event} />
                ))}
              </div>
            ) : (
              <p className="text-gray-400 text-sm">No active events right now.</p>
            )}
          </div>
        )}

        {/* Settings tab */}
        {activeTab === "settings" && (
          <div className="flex flex-col gap-6 max-w-lg">
            <h2 className="text-lg font-semibold text-gray-900">Settings</h2>

            <div className="flex flex-col gap-4 p-5 border border-gray-200 rounded-xl">
              <h3 className="font-medium text-gray-900">Profile</h3>
              <div className="flex items-center gap-4">
                {user?.picture && <img src={user.picture} alt={user.name} className="w-12 h-12 rounded-full" />}
                <div className="flex flex-col gap-1">
                  <span className="font-medium text-gray-900">{user?.name}</span>
                  <span className="text-sm text-gray-500">{user?.email}</span>
                </div>
              </div>
            </div>

            <div className="flex flex-col gap-4 p-5 border border-red-100 rounded-xl">
              <h3 className="font-medium text-red-700">Danger zone</h3>
              <p className="text-sm text-gray-500">
                Deleting your account removes all your tracked games, pity data, and pinned banners permanently.
              </p>
              <button className="w-fit px-4 py-2 text-sm text-red-600 border border-red-200 rounded-lg hover:bg-red-50">
                Delete account
              </button>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}
