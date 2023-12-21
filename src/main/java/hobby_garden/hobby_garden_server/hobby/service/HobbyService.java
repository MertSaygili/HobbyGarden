package hobby_garden.hobby_garden_server.hobby.service;


import hobby_garden.hobby_garden_server.hobby.model.Hobby;

public interface HobbyService {
    Hobby createHobbyToUser(String hobbyName);
}
