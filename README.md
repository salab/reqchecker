# reqchecker
A lint tool for checking requirements specification documents

## Usage
```
java -jar reqchecker.jar path/to/input.txt path/to/cabocha path/to/output.html
```
- `path/to/input.txt`: the path of the input file (plaintext)
- `path/to/cabocha`: the path of CaboCha execution file
- `path/to/output.html`: the path of the output file (HTML to be stored)

## Requirements
- CaboCha (https://taku910.github.io/cabocha/)
- Japanese Wordnet (https://bond-lab.github.io/wnja/)
    - Put the database file (`wnjpn.db`） into `src/main/resouces/`
- Java

## Installation and Execution Guide
This guide assumes that the user uses Mac OS X.

### Installing Prerequisites
```
$ brew install cabocha
```

### Building reqchecker
```
$ git clone https://github.com/salab/reqchecker.git
$ cd reqchecker
$ (cd src/main/resources && \
   curl -LO https://github.com/bond-lab/wnja/releases/download/v1.1/wnjpn.db.gz && \
   gunzip wnjpn.db.gz)
$ ./gradlew shadowJar
$ cp build/libs/reqchecker-all.jar ./reqchecker.jar
```
Pre-built runnable jars are also available at https://github.com/salab/reqchecker/releases

### Run reqchecker
```
$ java -jar reqchecker.jar sample/airline.txt /usr/local/bin/cabocha airline.html
$ open airline.html
```

## Related Publications

If you use this tool in a scientific publication, we would appreciate citations to the following paper:

Shinpei Hayashi, Ken Aruga, Motoshi Saeki: ``reqchecker: A Tool for Detecting Problems in Japanese Requirements Specification Documents Based on IEEE 830 Quality Characteristics'' (in Japanese), IEICE Transactions on Information and Systems, Vol. J101-D, No. 1, pp. 57-67, 2018. https://doi.org/10.14923/transinfj.2017SKP0036
```
@article{hayashi-ieicet201801,
    author = {Shinpei Hayashi and Ken Aruga and Motoshi Saeki},
    title = {{reqchecker}: {A} Tool for Detecting Problems in {Japanese} Requirements Specification Documents Based on {IEEE 830} Quality Characteristics},
    journal = {IEICE Transactions on Information and Systems},
    volume = {J101-D},
    number = 1,
    pages = {57--67},
    year = 2018,
    doi = {10.14923/transinfj.2017SKP0036}
}
```

(Japanese version)
林 晋平, 有賀 顕, 佐伯 元司: ``reqchecker：IEEE 830の品質特性に基づく日本語要求仕様書の問題点検出ツール'', 電子情報通信学会論文誌, Vol. J101-D, No. 1, pp. 57-67, 2018. https://doi.org/10.14923/transinfj.2017SKP0036
```
@article{hayashi-ieicet201801,
    author = {林 晋平 and 有賀 顕 and 佐伯 元司},
    title = {{reqchecker}：{IEEE 830}の品質特性に基づく日本語要求仕様書の問題点検出ツール},
    journal = {電子情報通信学会論文誌},
    volume = {J101-D},
    number = 1,
    pages = {57--67},
    year = 2018,
    doi = {10.14923/transinfj.2017SKP0036}
}
```
