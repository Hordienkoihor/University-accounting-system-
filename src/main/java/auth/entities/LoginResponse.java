package auth.entities;

public record LoginResponse<T extends User>(T user, int token) {
}
