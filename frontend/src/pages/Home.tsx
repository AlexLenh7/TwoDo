import { useAuth } from "../context/AuthContext.tsx";
import { useGames, useActiveBanners, useActiveEvents } from "../utils/Usedata.ts";
import GameCard from "../components/GameCard.tsx";
import BannerCard from "../components/BannerCard.tsx";
import EventCard from "../components/EventCard.tsx";
import Navbar from "../components/Navbar.tsx";
import LoginButton from "./Login.tsx";

export default function Home() {
  const { isAuthenticated } = useAuth();
  const { data: games, loading: gamesLoading } = useGames();
  const { data: banners, loading: bannersLoading } = useActiveBanners();
  const { data: events, loading: eventsLoading } = useActiveEvents();

  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />

      <main className="flex flex-col gap-16 px-6 py-12 max-w-6xl mx-auto w-full">
        {/* Hero section */}
        <section className="flex flex-col gap-6 items-center text-center py-8">
          <h1 className="text-4xl font-bold text-gray-900">Plan your pulls. Stop missing banners.</h1>
          <p className="text-lg text-gray-500 max-w-2xl">
            PityPlanner tracks your pity, currency, and upcoming banners across Wuthering Waves, Honkai: Star Rail, and
            Arknights: Endfield — so you always know when you can guarantee your next character.
          </p>
          {!isAuthenticated && (
            <div className="flex items-center gap-4">
              <button
                onClick={LoginButton}
                className="px-6 py-3 bg-black text-white rounded-lg hover:bg-gray-800 font-medium"
              >
                Start tracking for free
              </button>
              <a href="#games" className="text-sm text-gray-500 hover:text-black">
                Browse games ↓
              </a>
            </div>
          )}
        </section>

        {/* Why use it */}
        <section className="flex flex-col gap-6">
          <h2 className="text-2xl font-semibold text-gray-900">Why PityPlanner?</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            {[
              {
                title: "Cross-game tracking",
                body: "One dashboard for all your gacha games. No more switching between five different tracker sites.",
              },
              {
                title: "Pull projection",
                body: "Know exactly how many days until you can guarantee a banner character based on your real daily income.",
              },
              {
                title: "Save or pull?",
                body: "Get a recommendation on whether to pull now or save for an upcoming banner you have pinned.",
              },
            ].map((feature) => (
              <div key={feature.title} className="flex flex-col gap-2 p-5 border border-gray-200 rounded-xl">
                <h3 className="font-semibold text-gray-900">{feature.title}</h3>
                <p className="text-sm text-gray-500">{feature.body}</p>
              </div>
            ))}
          </div>
        </section>

        {/* Supported games */}
        <section id="games" className="flex flex-col gap-6">
          <div className="flex items-center justify-between">
            <h2 className="text-2xl font-semibold text-gray-900">Supported games</h2>
            <span className="text-sm text-gray-400">{games?.length ?? 0} games</span>
          </div>

          {gamesLoading ? (
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              {[1, 2, 3].map((i) => (
                <div key={i} className="h-36 bg-gray-100 rounded-xl animate-pulse" />
              ))}
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              {games?.map((game) => (
                <GameCard key={game.id} game={game} />
              ))}
            </div>
          )}
        </section>

        {/* Active banners */}
        <section className="flex flex-col gap-6">
          <h2 className="text-2xl font-semibold text-gray-900">Current banners</h2>

          {bannersLoading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {[1, 2, 3].map((i) => (
                <div key={i} className="h-44 bg-gray-100 rounded-xl animate-pulse" />
              ))}
            </div>
          ) : banners && banners.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {banners.map((banner) => (
                <BannerCard key={banner.id} banner={banner} />
              ))}
            </div>
          ) : (
            <p className="text-gray-400 text-sm">No active banners right now.</p>
          )}
        </section>

        {/* Active events */}
        <section className="flex flex-col gap-6">
          <h2 className="text-2xl font-semibold text-gray-900">Current events</h2>

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
            <p className="text-gray-400 text-sm">No active events right now.</p>
          )}
        </section>

        {/* Guide */}
        <section className="flex flex-col gap-6">
          <h2 className="text-2xl font-semibold text-gray-900">How it works</h2>
          <div className="flex flex-col gap-4">
            {[
              {
                step: "1",
                title: "Sign in with Google",
                body: "Create your free account in one click.",
              },
              {
                step: "2",
                title: "Add your games",
                body: "Select the games you play and input your current pity and currency.",
              },
              {
                step: "3",
                title: "Pin banners you want",
                body: "Mark upcoming banners you're saving for.",
              },
              {
                step: "4",
                title: "Get your projection",
                body: "PityPlanner tells you exactly when you'll be able to guarantee each banner.",
              },
            ].map((step) => (
              <div key={step.step} className="flex items-start gap-4 p-4 border border-gray-200 rounded-xl">
                <span className="flex items-center justify-center w-8 h-8 bg-black text-white text-sm font-bold rounded-full shrink-0">
                  {step.step}
                </span>
                <div className="flex flex-col gap-1">
                  <h3 className="font-medium text-gray-900">{step.title}</h3>
                  <p className="text-sm text-gray-500">{step.body}</p>
                </div>
              </div>
            ))}
          </div>
        </section>

        {/* CTA */}
        {!isAuthenticated && (
          <section className="flex flex-col items-center gap-4 py-8 border-t border-gray-200">
            <p className="text-gray-500">Ready to stop guessing?</p>
            <button
              onClick={LoginButton}
              className="px-6 py-3 bg-black text-white rounded-lg hover:bg-gray-800 font-medium"
            >
              Sign in with Google — it's free
            </button>
          </section>
        )}
      </main>
    </div>
  );
}
