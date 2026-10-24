package com.example.bff;

import com.example.bff.failure.UnknownReason;

record Unknown<T>(UnknownReason reason)
        implements RemoteOutcome<T> {}
