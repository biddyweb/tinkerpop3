#!/bin/bash

pushd "$(dirname $0)/../.." > /dev/null

if [ ! -f bin/gremlin.sh ]; then
  echo "Gremlin REPL is not available. Cannot preprocess AciiDoc files."
  popd > /dev/null
  exit 1
fi

for input in $(find docs/src/ -name "*.asciidoc")
do
  name=`basename $input`
  output="docs/${name}"
  echo "${input} > ${output}"
  if [ $(grep -c '^\[gremlin' $input) -gt 0 ]; then
    bin/gremlin.sh -e docs/preprocessor/processor.groovy $input > $output
    ec=$?
    if [ $ec -ne 0 ]; then
      popd > /dev/null
      exit $ec
    fi
  else
    cp $input $output
  fi
done

popd > /dev/null
