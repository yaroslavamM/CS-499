export type Location = {
  latitude: number;
  longitude: number;
};

export type Sex = 'INTACT_MALE' | 'INTACT_FEMALE' | 'NEUTERED_MALE' | 'SPAYED_FEMALE';

export type AnimalType = 'DOG' | 'CAT' | 'BIRD' | 'RABBIT' | 'OTHER';

export type Animal = {
  id: string;
  breed: string;
  checkInDate: string; // ISO date string
  checkOutDate: string | null;
  color: string;
  dateOfBirth: string; // ISO date string
  location: Location;
  name: string;
  outcome: string | null;
  outcomeDescription: string | null;
  sex: Sex;
  type: AnimalType;
};
