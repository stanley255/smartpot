#include <iostream>
#include <string>
#include <time.h>

using namespace std;

class Hash {
public:
	string generated_hash;
	int hashLength, countNumbers, countUpperLetters, countLowerLetters, countSpecialChars;
	int countTypesOfParams = 4; // Konstanta urcujuca pocet typov argmunetov

	int getRandomNumber() {
		return rand() % (10);
	}
	char getRandomUppLett() {
		return rand() % (25) + 65;
	}
	char getRandomLowLett() {
		return rand() % (25) + 97;
	}
	char getRandomSpecChar() {
		return rand() % (14) + 34;
	}
public:
	Hash(int countNums, int countUppLett, int countLowLett, int countSpecChars) {
		countNumbers = countNums;
		countUpperLetters = countUppLett;
		countLowerLetters = countLowLett;
		countSpecialChars = countSpecChars;
		hashLength = countNums + countUppLett + countLowLett + countSpecChars;
	}
	string getHash() {
		int initialLength = 0;
		int missCounter = 0;
		while (initialLength != hashLength) {
			int randomType = rand() % countTypesOfParams;
			switch (randomType) {
			case 0:
				if (countNumbers) {
					generated_hash += to_string(this->getRandomNumber());
					countNumbers--;
					initialLength++;
				}
				break;
			case 1:
				if (countUpperLetters) {
					generated_hash += this->getRandomUppLett();
					countUpperLetters--;
					initialLength++;
				}
				break;
			case 2:
				if (countLowerLetters) {
					generated_hash += this->getRandomLowLett();
					countLowerLetters--;
					initialLength++;
				}
				break;
			case 3:
				if (countSpecialChars) {
					generated_hash += this->getRandomSpecChar();
					countSpecialChars--;
					initialLength++;
				}
				break;
			default:
				missCounter++;
			}
		}
		return generated_hash;
	}
};

int main() {
	srand(time(NULL));
	Hash my_hash = Hash(8, 8, 8, 8);
	cout << my_hash.getHash() << endl;
	return 0;
}