import type { EventResponse } from "../types/event";

interface EventCardProps {
  event: EventResponse;
}

function formatDate(dateStr: string) {
  return new Date(dateStr).toLocaleDateString("en-US", {
    month: "short",
    day: "numeric",
  });
}

function daysRemaining(endDate: string) {
  const diff = new Date(endDate).getTime() - Date.now();
  return Math.max(0, Math.ceil(diff / (1000 * 60 * 60 * 24)));
}

export default function EventCard({ event }: EventCardProps) {
  return (
    <div className="flex flex-col gap-2 p-4 border border-gray-200 rounded-xl">
      <div className="flex items-start justify-between">
        <h3 className="font-medium text-gray-900">{event.name}</h3>
        <span className="text-xs text-gray-500">{event.gameName}</span>
      </div>
      <div className="flex items-center justify-between text-sm text-gray-500">
        <span>
          {formatDate(event.startDate)} — {formatDate(event.endDate)}
        </span>
        <span>{daysRemaining(event.endDate)}d left</span>
      </div>
      <div className="flex items-center gap-1 text-sm font-medium text-green-700">
        <span>+{event.totalCurrency}</span>
        <span className="text-gray-500 font-normal">{event.gameName} currency</span>
      </div>
    </div>
  );
}
