import { useState } from "react";
import { updateUserGames } from "../api/userGamesService";
import type { UserGamesResponse } from "../types/userGames";

interface PityTrackerProps {
  userGame: UserGamesResponse;
  userId: string;
  onUpdate: () => void;
}

export default function PityTracker({ userGame, userId, onUpdate }: PityTrackerProps) {
  const [currency, setCurrency] = useState(userGame.currentCurrency);
  const [pityChar, setPityChar] = useState(userGame.currentPityCharacter);
  const [pityWeapon, setPityWeapon] = useState(userGame.currentPityWeapon);
  const [saving, setSaving] = useState(false);

  // Pulls needed to guarantee from current pity
  const pullsNeededChar = Math.max(0, userGame.pityLimit - pityChar);
  const currencyNeeded = pullsNeededChar * 160;
  const currencyShortfall = Math.max(0, currencyNeeded - currency);

  const handleSave = async () => {
    setSaving(true);
    try {
      await updateUserGames(userId, userGame.id, {
        gameId: userGame.gameId,
        currentCurrency: currency,
        currentPityCharacter: pityChar,
        currentPityWeapon: pityWeapon,
      });
      onUpdate();
    } catch {
      alert("Failed to save");
    } finally {
      setSaving(false);
    }
  };

  const pityCharPercent = Math.min(100, Math.round((pityChar / userGame.pityLimit) * 100));

  return (
    <div className="flex flex-col gap-4 p-5 border border-gray-200 rounded-xl">
      <div className="flex items-center justify-between">
        <h3 className="font-semibold text-gray-900">{userGame.gameName}</h3>
        <span className="text-sm text-gray-500">{userGame.currencyName}</span>
      </div>

      {/* Pity progress bar */}
      <div className="flex flex-col gap-1">
        <div className="flex justify-between text-xs text-gray-500">
          <span>Character pity</span>
          <span>
            {pityChar} / {userGame.pityLimit}
          </span>
        </div>
        <div className="w-full h-2 bg-gray-100 rounded-full overflow-hidden">
          <div className="h-full bg-black rounded-full transition-all" style={{ width: `${pityCharPercent}%` }} />
        </div>
      </div>

      {/* Projection summary */}
      <div className="flex flex-col gap-1 p-3 bg-gray-50 rounded-lg text-sm">
        <div className="flex justify-between">
          <span className="text-gray-500">Current currency</span>
          <span className="font-medium">{currency.toLocaleString()}</span>
        </div>
        <div className="flex justify-between">
          <span className="text-gray-500">Pulls to guarantee</span>
          <span className="font-medium">{pullsNeededChar}</span>
        </div>
        <div className="flex justify-between">
          <span className="text-gray-500">Currency needed</span>
          <span className="font-medium">{currencyNeeded.toLocaleString()}</span>
        </div>
        {currencyShortfall > 0 && (
          <div className="flex justify-between text-red-600">
            <span>Shortfall</span>
            <span className="font-medium">-{currencyShortfall.toLocaleString()}</span>
          </div>
        )}
        {currencyShortfall === 0 && (
          <div className="flex justify-between text-green-600">
            <span>Status</span>
            <span className="font-medium">Ready to guarantee</span>
          </div>
        )}
      </div>

      {/* Edit fields */}
      <div className="flex flex-col gap-3">
        <div className="flex flex-col gap-1">
          <label className="text-xs text-gray-500">{userGame.currencyName}</label>
          <input
            type="number"
            value={currency}
            onChange={(e) => setCurrency(Number(e.target.value))}
            className="px-3 py-2 text-sm border border-gray-200 rounded-lg focus:outline-none focus:border-gray-400"
          />
        </div>
        <div className="flex gap-3">
          <div className="flex flex-col gap-1 flex-1">
            <label className="text-xs text-gray-500">Character pity</label>
            <input
              type="number"
              value={pityChar}
              min={0}
              max={userGame.pityLimit}
              onChange={(e) => setPityChar(Number(e.target.value))}
              className="px-3 py-2 text-sm border border-gray-200 rounded-lg focus:outline-none focus:border-gray-400"
            />
          </div>
          <div className="flex flex-col gap-1 flex-1">
            <label className="text-xs text-gray-500">Weapon pity</label>
            <input
              type="number"
              value={pityWeapon}
              min={0}
              onChange={(e) => setPityWeapon(Number(e.target.value))}
              className="px-3 py-2 text-sm border border-gray-200 rounded-lg focus:outline-none focus:border-gray-400"
            />
          </div>
        </div>
      </div>

      <button
        onClick={handleSave}
        disabled={saving}
        className="px-4 py-2 text-sm bg-black text-white rounded-lg hover:bg-gray-800 disabled:opacity-50"
      >
        {saving ? "Saving..." : "Save"}
      </button>
    </div>
  );
}
