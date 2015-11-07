# Makefile for triangle

PLATFORM := osx
MY_NAME := libtriangulation

CC := llvm-gcc
JAVAC := javac

ERASE_FILE ?= rm -f
ERASE_DIR ?= rm -r
MAKE_DIR ?= mkdir -p
COPY_FILE ?= cp
MAKE_LIB ?= g++ -dynamiclib -o
ARCH ?= -arch x86_64

SCRATCH_DIR := ../../../scratch/main/c
LIB_PATH := ../../../scratch/main/c
LIB_OUTPUT := ../../../lib
BIN_OUTPUT := ../../..lbin

MACOSX_DEPLOYMENT_TARGET ?= 10.6
SYS_ROOT ?= /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX10.11.sdk

CXXFLAGS := -DTRILIBRARY -D_REENTRANT -dynamic -fPIC -fno-common -Wall -O3 $(ARCH)
CXXFLAGS := $(CXXFLAGS) -mmacosx-version-min=$(MACOSX_DEPLOYMENT_TARGET)
#CXXFLAGS := $(CXXFLAGS) -isysroot $(SYS_ROOT)

LDFLAGS := -DTRILIBRARY -D_REENTRANT -dynamic -fPIC -Wall  $(ARCH)
LDFLAGS := $(LDFLAGS) -mmacosx-version-min=$(MACOSX_DEPLOYMENT_TARGET)
#LDFLAGS := $(LDFLAGS) -Wl,-syslibroot,$(SYS_ROOT)

OUTPUT_DIR := $(LIB_OUTPUT)
FINAL_TARGET := $(OUTPUT_DIR)/$(MY_NAME).jnilib
SOURCE_DIRS := .

C_SOURCES := $(foreach dir, $(SOURCE_DIRS), $(wildcard $(dir)/*.c))
INCLUDES := $(foreach dir, $(SOURCE_DIRS), -I$(dir))

OBJECTS := $(patsubst %.c, $(SCRATCH_DIR)/%.c.o, $(C_SOURCES))


.PHONY: all
all: _make_some_room_ $(FINAL_TARGET)


$(FINAL_TARGET): $(OBJECTS)
	$(MAKE_DIR) $(OUTPUT_DIR)
	$(MAKE_LIB) $@ $(CXXFLAGS) $+

$(SCRATCH_DIR)/%.c.o: %.c
	$(MAKE_DIR) $(dir $@)
	$(CC) -o $@ -c $< $(CXXFLAGS) $(INCLUDES)

$(OUTPUT_DIR)/%.h:
	$(MAKE_DIR) $(OUTPUT_DIR)
	$(COPY_FILE) $*.h $(OUTPUT_DIR)/$(notdir $*.h)

.PHONY: _make_some_room_
_make_some_room_:
	@echo
	@echo
	@echo Making $(MY_NAME)
	@echo

.PHONY: clean
clean:
	$(ERASE_DIR) $(SCRATCH_DIR)

.PHONY: _clean_me_/%
_clean_me_/%:
	$(ERASE_FILE) $*
