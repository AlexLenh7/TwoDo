import api from "./axiosConfig";
import type { UserRequest, UserResponse } from "../types/user";

// returns user profile
export const getUserProfile = async (id: string): Promise<UserResponse> => {
  const response = await api.get(`/api/user/${id}`);
  return response.data;
};

// updates username
export const updateUserName = async (id: string, user: UserRequest): Promise<UserResponse> => {
  const response = await api.put(`/api/user/${id}`, user);
  return response.data;
};

// deletes user
export const deleteUser = async (id: string): Promise<void> => {
  await api.delete(`/api/user/${id}`);
};
