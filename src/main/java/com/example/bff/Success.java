package com.example.bff;

record Success<T>(T value) implements RemoteOutcome<T> {}


