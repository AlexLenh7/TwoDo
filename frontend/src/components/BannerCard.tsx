import type { BannerResponse } from "../types/banner";

interface BannerCardProps {
  banner: BannerResponse;
  onPin?: (bannerId: string) => void;
  isPinned?: boolean;
  isLoading?: boolean;
}

function formatDate(dateStr: string) {
  return new Date(dateStr).toLocaleDateString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
  });
}

function daysRemaining(endDate: string) {
  const diff = new Date(endDate).getTime() - Date.now();
  return Math.max(0, Math.ceil(diff / (1000 * 60 * 60 * 24)));
}

export default function BannerCard({ banner, onPin, isPinned, isLoading }: BannerCardProps) {
  return (
    <div className="flex flex-col gap-3 p-5 border border-gray-200 rounded-xl">
      <div className="flex items-start justify-between">
        <div className="flex flex-col gap-1">
          <h3 className="font-semibold text-gray-900">{banner.characterName}</h3>
          <span className="text-sm text-gray-500">{banner.gameName}</span>
        </div>
        <div className="flex flex-col items-end gap-1">
          <span className="text-xs px-2 py-1 bg-gray-100 rounded-full text-gray-600">{banner.bannerType}</span>
          {banner.isLimited && (
            <span className="text-xs px-2 py-1 bg-yellow-100 text-yellow-700 rounded-full">Limited</span>
          )}
          {banner.isRerun && <span className="text-xs px-2 py-1 bg-blue-100 text-blue-700 rounded-full">Rerun</span>}
        </div>
      </div>

      <div className="flex flex-col gap-1 text-sm text-gray-500">
        <span>
          {formatDate(banner.startDate)} — {formatDate(banner.endDate)}
        </span>
        <span className="font-medium text-gray-700">{daysRemaining(banner.endDate)} days remaining</span>
      </div>

      {onPin && (
        <button
          onClick={() => onPin(banner.id)}
          disabled={isLoading}
          className={`mt-auto px-4 py-2 text-sm rounded-lg transition-colors ${
            isPinned ? "bg-gray-100 text-gray-500 hover:bg-gray-200" : "bg-black text-white hover:bg-gray-800"
          } ${isLoading ? "opacity-50 cursor-not-allowed" : ""}`}
        >
          {isLoading ? "Updating..." : isPinned ? "Unpin banner" : "Pin banner"}
        </button>
      )}
    </div>
  );
}
