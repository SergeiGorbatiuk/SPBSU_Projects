#pragma once
#include "calculator_resources.h"


LongNumber* longnum_init();

void digit_add_end(LongNumber* lnum,  char c);

void digit_add_pre(LongNumber* lnum, char c);

void longnum_destroy(LongNumber *lnum);
