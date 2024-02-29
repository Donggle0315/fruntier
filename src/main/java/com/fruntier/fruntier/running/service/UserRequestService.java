package com.fruntier.fruntier.running.service;

import com.fruntier.fruntier.running.domain.Coordinate;
import com.fruntier.fruntier.running.domain.UserRequest;
import com.fruntier.fruntier.running.domain.Vertex;

import java.util.Map;

public interface UserRequestService {
    Vertex convertCoordinateToVertex(Coordinate coordinate);
}
