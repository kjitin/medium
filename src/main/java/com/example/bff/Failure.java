package com.example.bff;

import com.example.bff.failure.RemoteFailure;

record Failure<T>(RemoteFailure failure)
        implements RemoteOutcome<T> {}
