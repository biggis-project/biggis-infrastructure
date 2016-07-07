################################################################################
#  Licensed to the Apache Software Foundation (ASF) under one
#  or more contributor license agreements.  See the NOTICE file
#  distributed with this work for additional information
#  regarding copyright ownership.  The ASF licenses this file
#  to you under the Apache License, Version 2.0 (the
#  "License"); you may not use this file except in compliance
#  with the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
# limitations under the License.
################################################################################

FROM wipa/kafka:0.9.0.0

#requirements
#RUN apt-get update && apt-get install -y inotify-tools expect-dev
RUN mkdir /kafka/rasterdir

ADD collector-daemon.sh /kafka/
VOLUME ["/kafka/rasterdir"]

ENTRYPOINT ["/kafka/collector-daemon.sh", "/kafka/rasterdir", "rasterqueue"]
