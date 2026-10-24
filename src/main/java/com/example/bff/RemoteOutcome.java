package com.example.bff;

public sealed interface RemoteOutcome<T>
        permits Success, Failure, Unknown {
    
}
