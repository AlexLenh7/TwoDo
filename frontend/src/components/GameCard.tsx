import { Link } from "react-router-dom";
import type { GameResponse } from "../types/game";

interface GameCardProps {
  game: GameResponse;
}

export default function GameCard({ game }: GameCardProps) {
  return (
    <Link
      to={`/games/${game.id}`}
      className="flex flex-col gap-2 p-5 border border-gray-200 rounded-xl hover:border-gray-400 transition-colors"
    >
      <h3 className="font-semibold text-gray-900">{game.name}</h3>
      <div className="flex flex-col gap-1 text-sm text-gray-500">
        <span>Currency: {game.currencyName}</span>
        <span>Pity limit: {game.pityLimit} pulls</span>
        <span>Cost per pull: {game.currencyPerPull}</span>
        <span>~{game.dailyIncome} free currency/day</span>
      </div>
      <span className="mt-auto text-sm text-blue-600 hover:underline">View game →</span>
    </Link>
  );
}
