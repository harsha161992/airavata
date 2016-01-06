/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#ifndef process_model_TYPES_H
#define process_model_TYPES_H

#include <iosfwd>

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>

#include <thrift/cxxfunctional.h>
#include "airavata_commons_types.h"
#include "application_io_models_types.h"
#include "status_models_types.h"
#include "task_model_types.h"
#include "scheduling_model_types.h"


namespace apache { namespace airavata { namespace model { namespace process {

class ProcessModel;

typedef struct _ProcessModel__isset {
  _ProcessModel__isset() : creationTime(false), lastUpdateTime(false), processStatus(false), processDetail(false), applicationInterfaceId(false), applicationDeploymentId(false), computeResourceId(false), processInputs(false), processOutputs(false), resourceSchedule(false), tasks(false), taskDag(false), processError(false), gatewayExecutionId(false), enableEmailNotification(false), emailAddresses(false), storageResourceId(false), userDn(false), generateCert(true), experimentDataDir(false) {}
  bool creationTime :1;
  bool lastUpdateTime :1;
  bool processStatus :1;
  bool processDetail :1;
  bool applicationInterfaceId :1;
  bool applicationDeploymentId :1;
  bool computeResourceId :1;
  bool processInputs :1;
  bool processOutputs :1;
  bool resourceSchedule :1;
  bool tasks :1;
  bool taskDag :1;
  bool processError :1;
  bool gatewayExecutionId :1;
  bool enableEmailNotification :1;
  bool emailAddresses :1;
  bool storageResourceId :1;
  bool userDn :1;
  bool generateCert :1;
  bool experimentDataDir :1;
} _ProcessModel__isset;

class ProcessModel {
 public:

  ProcessModel(const ProcessModel&);
  ProcessModel& operator=(const ProcessModel&);
  ProcessModel() : processId("DO_NOT_SET_AT_CLIENTS"), experimentId(), creationTime(0), lastUpdateTime(0), processDetail(), applicationInterfaceId(), applicationDeploymentId(), computeResourceId(), taskDag(), gatewayExecutionId(), enableEmailNotification(0), storageResourceId(), userDn(), generateCert(false), experimentDataDir() {
  }

  virtual ~ProcessModel() throw();
  std::string processId;
  std::string experimentId;
  int64_t creationTime;
  int64_t lastUpdateTime;
   ::apache::airavata::model::status::ProcessStatus processStatus;
  std::string processDetail;
  std::string applicationInterfaceId;
  std::string applicationDeploymentId;
  std::string computeResourceId;
  std::vector< ::apache::airavata::model::application::io::InputDataObjectType>  processInputs;
  std::vector< ::apache::airavata::model::application::io::OutputDataObjectType>  processOutputs;
   ::apache::airavata::model::scheduling::ComputationalResourceSchedulingModel resourceSchedule;
  std::vector< ::apache::airavata::model::task::TaskModel>  tasks;
  std::string taskDag;
   ::apache::airavata::model::commons::ErrorModel processError;
  std::string gatewayExecutionId;
  bool enableEmailNotification;
  std::vector<std::string>  emailAddresses;
  std::string storageResourceId;
  std::string userDn;
  bool generateCert;
  std::string experimentDataDir;

  _ProcessModel__isset __isset;

  void __set_processId(const std::string& val);

  void __set_experimentId(const std::string& val);

  void __set_creationTime(const int64_t val);

  void __set_lastUpdateTime(const int64_t val);

  void __set_processStatus(const  ::apache::airavata::model::status::ProcessStatus& val);

  void __set_processDetail(const std::string& val);

  void __set_applicationInterfaceId(const std::string& val);

  void __set_applicationDeploymentId(const std::string& val);

  void __set_computeResourceId(const std::string& val);

  void __set_processInputs(const std::vector< ::apache::airavata::model::application::io::InputDataObjectType> & val);

  void __set_processOutputs(const std::vector< ::apache::airavata::model::application::io::OutputDataObjectType> & val);

  void __set_resourceSchedule(const  ::apache::airavata::model::scheduling::ComputationalResourceSchedulingModel& val);

  void __set_tasks(const std::vector< ::apache::airavata::model::task::TaskModel> & val);

  void __set_taskDag(const std::string& val);

  void __set_processError(const  ::apache::airavata::model::commons::ErrorModel& val);

  void __set_gatewayExecutionId(const std::string& val);

  void __set_enableEmailNotification(const bool val);

  void __set_emailAddresses(const std::vector<std::string> & val);

  void __set_storageResourceId(const std::string& val);

  void __set_userDn(const std::string& val);

  void __set_generateCert(const bool val);

  void __set_experimentDataDir(const std::string& val);

  bool operator == (const ProcessModel & rhs) const
  {
    if (!(processId == rhs.processId))
      return false;
    if (!(experimentId == rhs.experimentId))
      return false;
    if (__isset.creationTime != rhs.__isset.creationTime)
      return false;
    else if (__isset.creationTime && !(creationTime == rhs.creationTime))
      return false;
    if (__isset.lastUpdateTime != rhs.__isset.lastUpdateTime)
      return false;
    else if (__isset.lastUpdateTime && !(lastUpdateTime == rhs.lastUpdateTime))
      return false;
    if (__isset.processStatus != rhs.__isset.processStatus)
      return false;
    else if (__isset.processStatus && !(processStatus == rhs.processStatus))
      return false;
    if (__isset.processDetail != rhs.__isset.processDetail)
      return false;
    else if (__isset.processDetail && !(processDetail == rhs.processDetail))
      return false;
    if (__isset.applicationInterfaceId != rhs.__isset.applicationInterfaceId)
      return false;
    else if (__isset.applicationInterfaceId && !(applicationInterfaceId == rhs.applicationInterfaceId))
      return false;
    if (__isset.applicationDeploymentId != rhs.__isset.applicationDeploymentId)
      return false;
    else if (__isset.applicationDeploymentId && !(applicationDeploymentId == rhs.applicationDeploymentId))
      return false;
    if (__isset.computeResourceId != rhs.__isset.computeResourceId)
      return false;
    else if (__isset.computeResourceId && !(computeResourceId == rhs.computeResourceId))
      return false;
    if (__isset.processInputs != rhs.__isset.processInputs)
      return false;
    else if (__isset.processInputs && !(processInputs == rhs.processInputs))
      return false;
    if (__isset.processOutputs != rhs.__isset.processOutputs)
      return false;
    else if (__isset.processOutputs && !(processOutputs == rhs.processOutputs))
      return false;
    if (__isset.resourceSchedule != rhs.__isset.resourceSchedule)
      return false;
    else if (__isset.resourceSchedule && !(resourceSchedule == rhs.resourceSchedule))
      return false;
    if (__isset.tasks != rhs.__isset.tasks)
      return false;
    else if (__isset.tasks && !(tasks == rhs.tasks))
      return false;
    if (__isset.taskDag != rhs.__isset.taskDag)
      return false;
    else if (__isset.taskDag && !(taskDag == rhs.taskDag))
      return false;
    if (__isset.processError != rhs.__isset.processError)
      return false;
    else if (__isset.processError && !(processError == rhs.processError))
      return false;
    if (__isset.gatewayExecutionId != rhs.__isset.gatewayExecutionId)
      return false;
    else if (__isset.gatewayExecutionId && !(gatewayExecutionId == rhs.gatewayExecutionId))
      return false;
    if (__isset.enableEmailNotification != rhs.__isset.enableEmailNotification)
      return false;
    else if (__isset.enableEmailNotification && !(enableEmailNotification == rhs.enableEmailNotification))
      return false;
    if (__isset.emailAddresses != rhs.__isset.emailAddresses)
      return false;
    else if (__isset.emailAddresses && !(emailAddresses == rhs.emailAddresses))
      return false;
    if (__isset.storageResourceId != rhs.__isset.storageResourceId)
      return false;
    else if (__isset.storageResourceId && !(storageResourceId == rhs.storageResourceId))
      return false;
    if (__isset.userDn != rhs.__isset.userDn)
      return false;
    else if (__isset.userDn && !(userDn == rhs.userDn))
      return false;
    if (__isset.generateCert != rhs.__isset.generateCert)
      return false;
    else if (__isset.generateCert && !(generateCert == rhs.generateCert))
      return false;
    if (__isset.experimentDataDir != rhs.__isset.experimentDataDir)
      return false;
    else if (__isset.experimentDataDir && !(experimentDataDir == rhs.experimentDataDir))
      return false;
    return true;
  }
  bool operator != (const ProcessModel &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const ProcessModel & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(ProcessModel &a, ProcessModel &b);

inline std::ostream& operator<<(std::ostream& out, const ProcessModel& obj)
{
  obj.printTo(out);
  return out;
}

}}}} // namespace

#endif
