APP       = executeit
all:
	g++ -o $(APP) main.cpp

clean:
	rm -f *.o *~* $(APP)

test:	$(APP)
	./cs130bProgram < test.txt
