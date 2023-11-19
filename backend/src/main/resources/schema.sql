CREATE TABLE IF NOT EXISTS Workout (
    id INTEGER AUTO_INCREMENT,
    date TIMESTAMP,
    duration INTEGER,
    notes text,
    rating INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT CHK_Workout CHECK ((RATING >= 1 AND RATING <= 5) OR RATING IS NULL)
);

CREATE TABLE IF NOT EXISTS Exercise (
    id INTEGER AUTO_INCREMENT,
    name text,
    description text,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Exercise_Done (
    id INTEGER AUTO_INCREMENT,
    workout INTEGER NOT NULL,
    weight INTEGER,
    sets INTEGER,
    reps INTEGER,
    exercise INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (exercise) REFERENCES Exercise(id) ON DELETE CASCADE,
    FOREIGN KEY (workout) REFERENCES Workout(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Muscle_Group (
    id INTEGER AUTO_INCREMENT,
    name text,
    description text,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Exercise_Muscle_Group (
    exercise INTEGER,
    muscle_group INTEGER,
    name text,
    PRIMARY KEY (exercise, muscle_group)
);

CREATE TABLE IF NOT EXISTS Users (
    id INTEGER AUTO_INCREMENT,
    username text,
    email text,
    password text,
    PRIMARY KEY (id)
);