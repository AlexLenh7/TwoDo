import api from "./axiosConfig";
import type { EventResponse, EventRequest } from "../types/event";

// returns all events
export const getAllEvents = async (): Promise<EventResponse[]> => {
  const response = await api.get("/api/events");
  return response.data;
};

// returns events by id
export const getEventById = async (id: string): Promise<EventResponse> => {
  const response = await api.get(`/api/events/${id}`);
  return response.data;
};

// returns event by game
export const getEventByGame = async (gameId: string): Promise<EventResponse[]> => {
  const response = await api.get(`/api/events/game/${gameId}`);
  return response.data;
};

// returns active events
export const getActiveEvents = async (): Promise<EventResponse[]> => {
  const response = await api.get("/api/events/active");
  return response.data;
};

// creates a event
export const createEvent = async (event: EventRequest): Promise<EventResponse> => {
  const response = await api.post("/api/events", event);
  return response.data;
};

// updates a event
export const updateEvent = async (id: string, event: EventRequest): Promise<EventResponse> => {
  const response = await api.put(`/api/events/${id}`, event);
  return response.data;
};

// deletes a event
export const deleteEvent = async (id: string): Promise<void> => {
  await api.delete(`/api/events/${id}`);
};
